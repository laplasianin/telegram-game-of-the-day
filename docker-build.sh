#!/usr/bin/env bash
export TERM="dumb"
printf "\n> \e[93m\033[1mBuilding Docker image\e[0m\n\n"
set -eo pipefail

ABSOLUTE_PATH=$(cd `dirname "${BASH_SOURCE[0]}"` && pwd)
cd ${ABSOLUTE_PATH}


docker build -t laplasianin/telebot:0.0.1 .