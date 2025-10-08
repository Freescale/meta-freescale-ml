SUMMARY = "The eiq examples based on Tf-lite"
DESCRIPTION = "The eiq examples based on Tf-lite"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "${EIQ_EXAMPLES_SRC};branch=${SRCBRANCH}"
EIQ_EXAMPLES_SRC ?= "git://github.com/nxp-imx/eiq-example.git;protocol=https"
SRCBRANCH = "lf-6.12.34_2.1.0"
SRCREV = "a900ed92dd27f79e832dca7b184c7f621ea0c97f"


do_install () {
    # install scripts to /usr/bin
    install -d ${D}${bindir}/${PN}-${PV}/
    cp ${S}/download_models.py ${D}${bindir}/${PN}-${PV}/
    cp -r ${S}/dms ${D}${bindir}/${PN}-${PV}/
    cp -r ${S}/face_recognition ${D}${bindir}/${PN}-${PV}/
    cp -r ${S}/image_classification ${D}${bindir}/${PN}-${PV}/
    cp -r ${S}/object_detection ${D}${bindir}/${PN}-${PV}/
    cp -r ${S}/gesture_detection ${D}${bindir}/${PN}-${PV}/
}

RDEPENDS:${PN} = "python3 python3-numpy python3-pillow python3-requests \
                  python3-opencv python3-pillow tensorflow-lite"

#Only support imx93 currently, will add other plantform support.
COMPATIBLE_MACHINE = "(mx93-nxp-bsp)"
