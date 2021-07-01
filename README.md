# Raymarcher

Raymarcher is a 3D rendering engine based in Java that uses [raymarching](https://en.wikipedia.org/wiki/Volume_ray_casting) to render 3D geometry. I made this project as a proof-of-concept to try and create 3D scenes and shapes within vanilla Java, while also to learn more about Raymarching and how it works. 

## Raymarching

[Raymarching](https://en.wikipedia.org/wiki/Volume_ray_casting) is a technique used to render 3D geometry with more photorealism than rasterization, while also having its own unique features and uses. It works by having each of the meshes and objects within the scene be defined by a [signed distance function](https://en.wikipedia.org/wiki/Signed_distance_function) (SDF) instead of by geometry, such as their vertices and faces. An SDF calculates the distance from a point in space to the mesh, with positive distances indicating the point is outside the mesh, a 0 distance indicating the point is on the mesh's surface, and a negative distance indicating that the point is inside the mesh.

In raymarching, rays are shot from the camera for each pixel on the screen. A ray will use the SDFs of the scene's objects to calculate the its distance to the scene, which will be the largest safe amount for it to march forwards without intersecting a mesh. The ray will then find the distance to the scene and continue marching until it has a small enough distance to the scene that it knows it has hit a mesh, at which point it colors its respective pixel on the screen. The collection of rays allow the scene to be rendered.

Raymarching is often a niche technique that is used because while it is computationally expensive, it can produce unique and interesting effects. Its main use within both realtime and non realtime rendering is for things like ambient occlusion and screenspace reflections, at it can perform these tasks more efficiently than ray tracing can and with better quality than rasterization. It is also unique in that because of its nature, it allows for the ability to warp and create non-Euclidean space, such as a closed, repeating world, with little extra compuatation. This makes it good for rendering infinitely repeating geometry, such as in a fractal. While these are all useful applications of raymarching, only the base raymarching techniques have been implemented in this program so far.

## Use

## Problems

Since this program is run on the JVM and does not use the GPU to caclulate scenes (which would require OpenGL intergration which would defeat my goal of creating this in vanilla Java), the framerate it can produce is relatively low. Meshes with more calculations in their signed distance function yield lower framerates and, on average, the program stabilizes at around 15 fps when rendering scenes with only 2-3 primitive meshes. Since raymarching is relatively computationally difficult for just a CPU, the best route to solve this issue would be to incorporate OpenGL and the GPU.

Other problems involve the movement and rotation of vectors and the camera in the scene. Typically, in 3D scenes, [quaternions](https://en.wikipedia.org/wiki/Quaternion) are used to rotate vectors in 3D space. However, since this was beyond the scope of my mathematical ability, I ended up using a version of the [Euler angle rotation system](https://mathworld.wolfram.com/EulerAngles.html) to rotate vectors and the camera. The downsides of this is that rotations in the program can only be performed on either the x, y, or z axis, as well as the fact that [gimbal lock](https://en.wikipedia.org/wiki/Gimbal_lock) may sometimes occur. 
