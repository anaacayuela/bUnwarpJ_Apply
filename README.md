# bUnwarpJ Apply

  This ImageJ/Fiji plugin was developed to provide a fast-execution and mainly, to be useful as a complementary for applying the correct transformation to the misaligned image provided by the user to obtain finally, a aligned version of your initial-misaligned image. This transformation was performed by the Fiji/ImageJ plugin bUnwarpJ and consist on a 2D image registration based on elastic deformations represented by B-splines. On the other hand, bUnwarpJ is able to perform the invertibility of the deformations through a consistency restriction. Basically, bUnwarpJ is an algorithm for elastic and consistent image registration which is capable to carry out the registration of two images (A and B) at the same time besides, this algorithm make a elastic deformation of the image A in order to look as equal as posible to image B. In this last step, the user will be able to obtain the inverse transformation (from image B to image A).
This plugin requires only one input parameter to determine the transformation of the misaligned image. This specific parameter is the transformation file provided by the bUnwarpJ plugin. At the point, it is essential for the user to choose the correct transformation file that follows the next nomenclature: Channel+imageTitle+_direct_transf.txt.
To achive a correct installation and launching, you should download the one single JAR file [bUnwarpJApply.jar](https://github.com/anaacayuela/bUnwarpJ_Apply/releases/download/1.0/bUnwarpJApply_.jar) and place it into the "plugins" folder of ImageJ/Fiji. Note that for a better understanding of this plugin, you shall do the following steps:

## •STEP 1.
Open the misaligned image file you want to analyze through ImageJ/Fiji -> Open....

![image](https://user-images.githubusercontent.com/54528366/67273617-1c2aab00-f4bf-11e9-9901-9246c8f3842a.png)

## •STEP 2.
Select bUnwarpJApply (ImageJ/Fiji ->Plugings -> bUnwarpJApply).

![image](https://user-images.githubusercontent.com/54528366/67273971-d28e9000-f4bf-11e9-9de5-25c42f65dfc7.png)

## •STEP 3. 
Fill the parameter gaps that appear in the GUI with your own direct transformation file. Note that the plugin is able to save the last directory/file path you put so you could use the same directory/file path as much as you need.

![image](https://user-images.githubusercontent.com/54528366/67274334-809a3a00-f4c0-11e9-9689-01907cd93228.png)

*At this point, this plugin advise the user to have choosen Channel 2 as Source in bUnwarpJ.

![image](https://user-images.githubusercontent.com/54528366/67274396-a1fb2600-f4c0-11e9-9731-735e42685734.png)

## •STEP 4.
In this step, the user will obtain the initial-misaligned image and the splitted channels images: Red channel with the transformation applied and the initial Green channel. Secondly, you should merge the channels to get the composite version and for being aware of the real results.

![image](https://user-images.githubusercontent.com/54528366/67274934-c99ebe00-f4c1-11e9-85fb-138065464432.png)

## STEP 7.
Finally, after merging channels, the ImageJ/Fiji plugin will generate a composite image where it is clearly easy to observe how powerful is the bUnwarpJ tool.

![image](https://user-images.githubusercontent.com/54528366/67275295-93ae0980-f4c2-11e9-8d3b-7a59271cc423.png)
*Initial misaligned image.

![image](https://user-images.githubusercontent.com/54528366/67275454-ef789280-f4c2-11e9-9258-faafbdca2a43.png)
*Final aligned image.






