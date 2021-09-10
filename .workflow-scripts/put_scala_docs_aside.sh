

# shellcheck disable=SC2010
scala_versions=$(ls "$1" | grep -Eo "[0-9]\.[0-9][0-9]")
api_version=$(sbt version | ansi2txt | tail -n1 | sed -r 's/^\[info\] (.+)$/\1/')

export scala_versions
export api_version

for scala_version in $scala_versions
do
  mkdir -p "$2/scala-$scala_version/$api_version"
  mv "$1/scala-$scala_version/api/" "$2/scala-$scala_version/$api_version/"
done
