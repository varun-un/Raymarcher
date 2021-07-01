# Raymarcher

Raymarcher is a primitive realtime 3D rendering engine based in Java that uses [raymarching](https://en.wikipedia.org/wiki/Volume_ray_casting) to render 3D geometry. I made this project as a proof-of-concept to try and create 3D scenes and shapes within vanilla Java, while also to learn more about raymarching and how it works. 

The program's full documentation can be found at: <https://varun-un.github.io/Raymarcher/>

## Raymarching

[Raymarching](https://en.wikipedia.org/wiki/Volume_ray_casting) is a technique used to render 3D geometry with more photorealism than rasterization, while also having its own unique features and uses. It works by having each of the meshes and objects within the scene be defined by a [signed distance function](https://en.wikipedia.org/wiki/Signed_distance_function) (SDF) instead of by geometry, such as their vertices and faces. An SDF calculates the distance from a point in space to the mesh, with positive distances indicating the point is outside the mesh, a 0 distance indicating the point is on the mesh's surface, and a negative distance indicating that the point is inside the mesh.

In raymarching, rays are shot from the camera for each pixel on the screen. A ray will use the SDFs of the scene's objects to calculate the its distance to the scene, which will be the largest safe amount for it to march forwards without intersecting a mesh. After marching forwards, the ray will repeat this process, finding the distance to the scene and continuing marching until it has a small enough distance to the scene that it assumes it has hit a mesh, at which point the ray colors its respective pixel on the screen with the corresponding color. The collection of rays allow the scene to be rendered.

Raymarching is often a niche technique that is used because while it is computationally expensive, it can produce unique and interesting effects. Its main use within both realtime and non realtime rendering is for things like ambient occlusion and screenspace reflections, at it can perform these tasks more efficiently than ray tracing can and with better quality than rasterization. It is also unique in that because of its nature, it allows for the ability to warp and create non-Euclidean space, such as a closed, repeating world, with little extra compuatation. This makes it good for rendering infinitely repeating geometry, such as in a fractal. While these are all useful applications of raymarching, only the base raymarching techniques have been implemented in this program so far.

## Use

### Controls

When a new instance of the engine is launched, keyboard controls can be used to move and rotate the camera as follows: <br>
W - move forward <br>
A - move to the left <br>
S - move backwards <br>
D - move to the right <br>
Space - move upwards <br>
Shift - move downwards <br>
<br>
Up Arrow - rotate around global x-axis in a +x direction <br>
Down Arrow - rotate around global x-axis in a -x direction <br> 
Left Arrow - rotate around global y-axis in a -y direction <br>
Right Arrow - rotate around global y-axis in a +y direction <br>

### Create your own scenes

The most important part of any 3D angine is the ability to render a variety of different shapes and scenes, which is what this guide will show you how to do for Raymarcher. The full documentation for all classes can be found at **<https://varun-un.github.io/Raymarcher/>**.


#### Scene

The first step is to create a main class from which to run the main method. Within the main method, the first step is to create a new scene, which can be done with the following line:
```java
Scene myScene = new Scene();
```
By default, the scene's world color is set to sky blue, but this can be changed with the `setWorldColor(Color color)` method. 

To add a mesh to the scene, simply use the `add(Mesh mesh)` method, passing in the mesh as a parameter, or add all the meshes you want to add to the scene at once with the `setMeshes(ArrayList<Mesh> meshes)` meshes, passing in an ArrayList of all the meshes you want to add.

#### Meshes

Since raymarching is used to render objects, rather than having these meshes be defined by geometry, they're defined by their SDFs. To create a mesh, you have to create an object which implements the `Mesh` interface. This can be done by either creating a new class for the mesh, or by using an anonymous inner class such as in the following:
```java
Mesh customMesh = new Mesh(){

  @Override
  public double sdf(Vector3 position) {           
    //enter the SDF function for this mesh here, returning the distance from position to the mesh
  }

  @Override
  public Color getMeshColor() {             
    //return the color of the mesh here
  }      
};
```
The `sdf(Vector3 position)` method takes in a [Vector3](https://varun-un.github.io/Raymarcher/raymarcher/Vector3.html) representing the position to calculate the distance to the mesh from, and returns the distance. The `getMeshColor()` method simply returns the color to render the mesh. 

Many of the SDFs which you would need to implement for primitive shapes can also be found [here](https://iquilezles.org/www/articles/distfunctions/distfunctions.htm) (though they are written in GLSL code and will require translation into Java), as well as functions for other unique quirks of raymarching.

Not all shapes need to be implemented manually however, as the program already includes classes for some primitive shapes pre-made. These, and information on how to use them, can be found in more detail in the [documentation](https://varun-un.github.io/Raymarcher/raymarcher/meshes/package-summary.html).

To use these pre-made mesh classes, first import the class or the entire package:
```java
import raymarcher.meshes.*;
```
Then, simply call the constructor to initialize one of these shapes. For example, to make a red sphere with a radius of 1 centered at the origin:
```java
Sphere sphere = new Sphere(new Vector3(0, 0, 0), 1, Color.RED);
```
Whatever mesh you create then has to be added to the scene for it to be rendered, which can be done with the `add(Mesh mesh)` method, as explained previously.

#### MeshGroups

One useful feature of raymarching is its ability to easily perform boolean operations on two meshes. There are three main boolean operations you can perform: union, which joins together two meshes; subtraction (also called difference), which cuts out one mesh from the other; and intersection, which yields the portion common to both meshes. These can be seen below, respectively.

![Union boolean operation](https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/Boolean_union.PNG/180px-Boolean_union.PNG) ![Subtraction boolean operation](https://upload.wikimedia.org/wikipedia/commons/thumb/8/86/Boolean_difference.PNG/180px-Boolean_difference.PNG) ![Intersection boolean operation](https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Boolean_intersect.PNG/180px-Boolean_intersect.PNG)

These operations can be done with the `MeshGroup` class. To create a MeshGroup, pass in the two meshes to create the group with, the operation to perform, and the color to make the group, with the constructor being:
```java
public MeshGroup(Mesh mesh1, Mesh mesh2, int operation, Color meshColor)
```
To set whichever operation you want to do, pass either `MeshGroup.UNION`, `MeshGroup.SUBTRACT`, or `MeshGroup.INTERSECTION` into the operation parameter. The MeshGroup can then be added to the scene the same way a mesh can be, by calling the `add(MeshGroup)` method on the scene object.

#### Camera

Once the scene has been set up, a camera needs to be created and given the scene. The camera represents the point of view from which the scene will be rendered and can be created via the following constructor: 
```java
public Camera(Vector3 position, Vector3 direction, Vector3 upDirection, double screenDistance, Scene scene)
```
`position` is the place in 3D space to place the camera, while `direction` and `upDirection` determine the direction that the camera is facing, and therefore its rotation. `screenDistance` has a similar effect to focal length in an actual camera and `scene` is the scene that was created which the camera should render.

The camera also includes getter and setter methods to manipulate its other aspects, such as its movement and rotation speeds, as well as the distance between pixels on the screen in scene units, which has an opposite effect as the camera's `screenDistance`.

#### Screen

The final step to set up and start the rendering engine is to create the engine instance to use for rendering. This is done by the creation of a `Screen` object, whose constructor is:
```java
public Screen(int screenWidth, int screenHeight, Camera camera, double framerate)
```
`screenWidth` and `screenHeight` define the dimensions of the window to create for the engine, and thus the resolution of the render. The camera object previously created also needs to be passed in, as well as an int to represent the framerate to run the program. Running the engine at a higher framerate than what the system can handle leads to dropped frames, so for each system it's best to experiment to find the optimal framerate.

## Current Drawbacks

Since this program is run on the JVM and does not use the GPU to caclulate scenes (which would require OpenGL intergration which would defeat my goal of creating this in vanilla Java), the framerate it can produce is relatively low. Meshes with more calculations in their SDF yield lower framerates and, on average, the program stabilizes at around 15 fps when rendering scenes with only 2-3 primitive meshes. Since raymarching is relatively computationally difficult for just a CPU, the best route to solve this issue would be to incorporate OpenGL and the GPU.

Other problems involve the movement and rotation of vectors and the camera in the scene. Typically, in 3D scenes, [quaternions](https://en.wikipedia.org/wiki/Quaternion) are used to rotate vectors in 3D space. However, since this was beyond the scope of my mathematical ability, I ended up using a version of the [Euler angle rotation system](https://mathworld.wolfram.com/EulerAngles.html) to rotate vectors and the camera. The downsides of this are that rotations in the program can only be performed on either the x, y, or z axis, as well as the fact that [gimbal lock](https://en.wikipedia.org/wiki/Gimbal_lock) may sometimes occur. 
