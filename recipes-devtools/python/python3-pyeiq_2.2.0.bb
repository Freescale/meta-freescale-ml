SUMMARY = "Python Framework for eIQ on i.MX Processors"
DESCRIPTION = "PyeIQ is written on top of eIQ ML Software Development Environment and \
provides a set of Python classes allowing the user to run Machine Learning applications \
in a simplified and efficiently way without spending time on cross-compilations, \
deployments or reading extensive guides."
HOMEPAGE = "https://pyeiq.dev/"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=352d686f958e763cb284c26430cc387d"

DEPENDS = "python3"

SRCBRANCH = "v2.0.0"
SRCREV = "0b51e18f423493326d210f3b5b4dce92fffd2a21"

SRC_URI = "git://source.codeaurora.org/external/imxsupport/pyeiq;protocol=https;branch=${SRCBRANCH}"

inherit setuptools3

S = "${WORKDIR}/git"
