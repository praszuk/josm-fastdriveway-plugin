# FastDriveway

FastDriveway allows creating driveways very fast.
It creates driveway filled with the necessary tags on way and nodes.

## Usage

1. Draw an empty way – it must start with node on way with tag `highway=*`!
2. Select the way
3. Press `CTRL + SHIFT + 4` – to create a driveway filled with tags. You can create footway too (`CTRL + SHIFT + 5`).

### What it does?

#### Driveway:
If way intersects `barrier=*`, it will be split into 2 ways. Objects will be filled with tags:
- First part of way (before barrier):
    ```
    highway=service
    service=driveway
    ```
- Intersection node (empty way with barrier):
    ```
    barrier=gate
    access=private
    ```
- Second part of way (after barrier)
    ```
    highway=service
    service=driveway
    access=private
    ```

Last node:
- is not connected to anything:
    ```
    noexit=yes
    ```
- is connected to `barrier=*`:
    ```
    barrier=gate
    access=private
    ```
- is connected to `building=*`:
    ```
    entrance=garage
    access=private
    ```

#### Footway:
The result is similar to driveway section, but there is a difference with way tag and last node on the building.
- Instead of `highway=service service=driveway` it will be replaced with `highway=footway`.
- Instead of `entrance=garage` it will be replaced with `entrance=yes`.

### Config
The plugin has the feature to set custom tags on above conditions in _FastDriveway config_ (`CTRL + SHIFT + 6`).
Changes are not persistent. They will be kept until JOSM exit.

### Additional notes
Note: It doesn't support more complicated cases like: multiple intersection, almost cycle (ending way on the same highway), building tunnel passages etc.


### License
[GPLv3](LICENSE)
