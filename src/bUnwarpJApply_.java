import java.io.File;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import bunwarpj.BSplineModel;
import bunwarpj.MiscTools;
import fiji.util.gui.GenericDialogPlus;
import histogram2.HistogramMatcher;
import ij.CompositeImage;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.measure.Measurements;
import ij.plugin.ChannelSplitter;
import ij.plugin.PlugIn;
import ij.plugin.RGBStackMerge;
import ij.process.ImageProcessor;
import ij.process.LUT;

public class bUnwarpJApply_ implements PlugIn, Measurements {
	private String title;
	private String TransfPath;
	private ImageStack imp1;
	private ImagePlus imp1p;
	private ImageStack imp0;
	private ImagePlus imp0p;
	private CompositeImage comp;
	private LUT lut1;
	
	@SuppressWarnings("deprecation")
	public void run(String arg) {
		 ImagePlus imp = IJ.getImage();
		 String title_aux =imp.getTitle();
		 if(title_aux.contains(".")) { 
			 title=title_aux.substring(0, title_aux.lastIndexOf('.'));
		 }
		 imp.setTitle(title);
	 	if (imp == null)
			 IJ.noImage();
	 	if (imp.getImageStackSize() == 1) {
	 		IJ.error("You should use the Merge Channels tool");
	 		return;
	 	}
	 	IJ.error("It's essential to have been choosen Channel 2 as Source in bUnwarpJ plugin!!!!");
	 	Preferences pref = Preferences.userRoot();
	 	TransfPath = pref.get("BUNWARPJAPPLY_DEFAULT_PATH", "");
 		if (!showDialog(imp))
 			return;
 		pref.put("BUNWARPJAPPLY_DEFAULT_PATH", TransfPath);
	 	if (imp.getNChannels() > 1) { 
			imp1 =ChannelSplitter.getChannel(imp, 2);
			imp1p = new ImagePlus ("C"+"2"+"-"+title,imp1);
			imp0 = ChannelSplitter.getChannel(imp, 1);
			imp0p = new ImagePlus ("C"+"1"+"-"+title, imp0);
			imp0p.getProcessor().resetMinAndMax();
			comp = new CompositeImage(imp);
			lut1 = comp.getChannelLut(1);
	 		}
			int intervalsSource=MiscTools.numberOfIntervalsOfTransformation(TransfPath);
			double [][]cxSource = new double[ intervalsSource+3 ][ intervalsSource+3 ];
			double [][]cySource = new double[ intervalsSource+3 ][ intervalsSource+3 ];
			MiscTools.loadTransformation(TransfPath, cxSource, cySource );
			ImageStack outputStackSource = 
					new ImageStack( imp1p.getWidth(), imp1p.getHeight() );
			for( int i=1; i<=imp1p.getImageStackSize(); i++ )
			{
			 	IJ.showStatus("bUnwarpJ Apply "+i+"/"+imp1p.getImageStackSize());
				ImageProcessor ipSource = imp1p.getImageStack().getProcessor( i );
				BSplineModel source = new BSplineModel( ipSource, false, 1 );
				ImagePlus movingImageSource = new ImagePlus("", ipSource );			
				ImageProcessor resultSource = 
						MiscTools.applyTransformationMT( 
							movingImageSource, imp0p, source, intervalsSource, cxSource, cySource );
				resultSource.setLut(lut1);
				resultSource.resetMinAndMax();
				outputStackSource.addSlice( "", resultSource );
			}
			ImagePlus imp1Final = new ImagePlus (imp1p.getTitle() +"-transformed",outputStackSource);
			if (imp0.getBitDepth()== 8) {
			IJ.run(imp1Final, "8-bit", "");
			}
			if (imp0.getBitDepth()== 16) {
				IJ.run(imp1Final, "16-bit", "");	
			}
			if (imp0.getBitDepth()== 24) {
				IJ.run(imp1Final, "RGB Color", "");
				}
			if (imp0.getBitDepth()== 32) {
			IJ.run(imp1Final, "32-bit", "");
			}
			ImageStack resStack = new ImageStack( imp0p.getWidth(), imp0p.getHeight() );
			HistogramMatcher matcher = new HistogramMatcher();
			ImageProcessor ipRef = imp.getStack().getProcessor(2);
			int [] histRef = ipRef.getHistogram();
			for( int slice =1; slice <= imp0p.getImageStackSize(); slice++ )
			{	
				ImageProcessor ipTarget = imp0p.getStack().getProcessor( slice );
				int[] histTarget = ipTarget.getHistogram();
				int [] newHist = matcher.matchHistograms(histTarget, histRef);
				ipTarget.applyTable(newHist);		
				resStack.addSlice(imp0p.getStack().getSliceLabel(slice), ipTarget);
			}
			ImagePlus result = new ImagePlus(imp0p.getTitle(), resStack );
			result.setCalibration( imp0p.getCalibration() );
			imp1Final.show();
			result.show();
			RGBStackMerge rsm = new RGBStackMerge();
			rsm.mergeStacks();	
	}
	
	boolean showDialog(ImagePlus imp) {
		GenericDialogPlus gd = new GenericDialogPlus("bUnwarpJ Apply");
		if (imp.getNChannels() > 1) {
			gd.addDirectoryOrFileField("Load the Transformation file (Filename like this: C2-imageTitle_direct_transf.txt\"", TransfPath);
			}
		gd.showDialog();
		if(gd.wasCanceled())
			return false;
		if(gd.invalidNumber()) {
			IJ.error("Error", "Invalid input number");
				return false;
		}
		TransfPath = gd.getNextString();
		return true;
		}
}
