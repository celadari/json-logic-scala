# -*- coding: utf-8 -*-
# Copyright 2019 celadari. All rights reserved. MIT license.

import argparse
import json
import os


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('path_json')
    parser.add_argument('api_version')
    parser.add_argument('--scala-versions', dest='scala_versions', nargs='+')
    args = parser.parse_args()

    if os.path.isfile(args.path_json):
        with open(args.path_json, 'r') as f:
            data = json.load(f)
    else:
        data = {}
    for scala_version in args.scala_versions:
        scala_version_indices = [i for i, data_version in enumerate(data) if data_version['scala-version'] == scala_version]
        if scala_version_indices:
            scala_version_index = scala_version_indices[0]
            data[scala_version_index]['json-logic-versions'] = [args.api_version] + data[scala_version_index]['json-logic-versions']
        else:
            data.append({'scala-version': scala_version, 'json-logic-versions': [args.api_version]})
    with open(args.path_json, 'w') as f:
        json.dump(data, f)
