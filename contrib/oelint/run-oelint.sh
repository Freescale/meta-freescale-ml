#!/bin/sh
# Run oelint-adv over this layer; its settings live in .oelint.cfg and
# oelint.constants.json at the layer root.
set -eu

# Neutralise CDPATH so 'cd' below can't print or jump to an unexpected dir.
unset CDPATH
# A personal $OELINT_CONFIG would replace ./.oelint.cfg.
unset OELINT_CONFIG

here=$(cd -- "$(dirname -- "$0")" && pwd)
layer=$(cd -- "$here/../.." && pwd)

# Both config files are looked up from the layer root.
cd -- "$layer"

# A lone *.inc is linted through its recipes; listing it is harmless.
files=$(find . \
    \( -name '*.bb' -o -name '*.bbappend' -o -name '*.bbclass' -o -name '*.inc' \
       -o \( -path './conf/*' -name '*.conf' \) \) \
    | sort)

# Serial: older oelint-adv releases report false findings in parallel runs.
# '--mode all': 'fast' skips machine/distro confs and 'branch:false' findings.
# shellcheck disable=SC2086
exec oelint-adv --jobs 1 --mode all "$@" $files
