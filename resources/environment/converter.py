"""
author: @nathan-nguyen (Slack: Nathan Nguyen) - contact me
  if there is any issue with this script.

What is this?
  This is a script that converts a json file created by the Tiled software
  to a json file that our game can load.
"""

import os
import sys
import json

def get_tiles(tiled_json_file_name):
    json_file = open(tiled_json_file_name, mode='r')
    json_file_content = json_file.read()
    json_file.close()

    json_input = json.loads(json_file_content)
    tilesets = json_input['tilesets']

    id_to_texture = {}

    for tileset in tilesets:
        tileset_id     = tileset['firstgid']
        tileset_source = tileset['source']   # should be a `.tsx` file
        texture        = tileset_source[:-4] # remove the `.tsx` extension
        id_to_texture[tileset_id] = texture

    height = json_input['height'] // 2
    width = json_input['width'] // 2

    texture_data = json_input['layers'][0]["data"]
    tiles = []

    tile_id = 0
    for row in range(height - 1, -height - 1, -1):
        tile_index = height * 2 - 1
        for col in range(-width, width):
            tile = {}
            tile["tileID"] = tile_id
            tile["texture"] = id_to_texture[texture_data[tile_id]]
            tile["obstructed"] = False
            tile["index"] = tile_index
            tile["rowPos"] = float(row)
            tile["colPos"] = float(col)
            tile_id += 1
            tile_index -= 1
            tiles.append(tile)

    return tiles


def write_to_output(output_json_file_name, tiles):
    json_output = {}

    # Create a playerPeon to ensure that no funny error happens
    entity = {}
    entity["objectName"] = "playerPeon"
    entity["speed"] = 0.1
    entity["texture"] = "spacman_ded"
    entity["entityID"] = 0
    entity["rowPos"] = 0.0
    entity["colPos"] = 0.0
    json_output["entities"] = []
    json_output["entities"].append(entity)

    json_output["tiles"] = tiles

    with open(output_json_file_name, 'w') as f:
        json.dump(json_output, f, indent=4)


def main(argv):
    if len(argv) < 2:
        if sys.platform == "linux" or sys.platform == "linux2" or sys.platform == "darwin":
            print("Usage: python3 tiled_converter.py <tiled_file>.json <output_file>.json")
        if sys.platform == "win32":
            print("Usage: python tiled_converter.py <tiled_file>.json <output_file>.json")
        return

    tiled_json_file_name = argv[0]
    output_json_file_name = argv[1]

    if not os.path.isfile(tiled_json_file_name):
        print("Invalid tiled input file: {0}".format(tiled_json_file_name))
        return

    if tiled_json_file_name[-5:] != '.json':
        print("Input file is not a json file: {0}".format(tiled_json_file_name))
        return

    if output_json_file_name[-5:] != '.json':
        print("Output file is not a json file: {0}".format(output_json_file_name))
        return

    tiles = get_tiles(tiled_json_file_name)

    write_to_output(output_json_file_name, tiles)


if __name__ == "__main__":
    main(sys.argv[1:])
