SUMMARY = "Shell Script Automated Tester (unit testing executable files)"
DESCRIPTION = "SSAT is a testing framework for test cases written as Bash scripts. It searches a path recursively for runTest.sh test groups, runs them with the helper functions of ssat-api.sh and summarises the results, exiting non-zero on a critical failure."
HOMEPAGE = "https://github.com/myungjoo/SSAT"
BUGTRACKER = "https://github.com/myungjoo/SSAT/issues"

SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://debian/copyright;md5=9ad9849cc2d52d5b8200d053d510e7d2"

SRCBRANCH = "main"
SRC_URI = "git://github.com/myungjoo/SSAT;protocol=https;branch=${SRCBRANCH}"

CVE_PRODUCT = "myungjoo:ssat"

do_install () {
     install -d ${D}${bindir}
     install -p -m 0755 ${S}/ssat.sh ${D}${bindir}/
     install -p -m 0644 ${S}/ssat-api.sh ${D}${bindir}/
     cd ${D}${bindir}
     ln -s ssat.sh ssat
}

FILES:${PN} += "${bindir}/ssat \
                ${bindir}/ssat.sh \
                ${bindir}/ssat-api.sh"
