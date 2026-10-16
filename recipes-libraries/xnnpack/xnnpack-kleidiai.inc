# XNNPACK's KleidiAI archive no longer matches its pinned hash, so fetch the
# same commit with git; KLEIDIAI_SOURCE_DIR then skips the download.
SRC_URI:append = " git://git.gitlab.arm.com/kleidi/kleidiai.git;protocol=https;branch=main;name=kleidiai;destsuffix=kleidiai"
SRCREV_kleidiai = "847ebd19d0192528659b0a0fa2c6057eed674c6a"

EXTRA_OECMAKE:append = " -DKLEIDIAI_SOURCE_DIR=${UNPACKDIR}/kleidiai"

# KleidiAI is C and unpacks outside S, so DEBUG_PREFIX_MAP does not cover it
CFLAGS:append = " -ffile-prefix-map=${WORKDIR}="
