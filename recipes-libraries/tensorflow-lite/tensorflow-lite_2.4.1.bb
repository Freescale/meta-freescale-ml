# Copyright 2020-2021 NXP
DESCRIPTION = "TensorFlow Lite C++ Library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=64a34301f8e355f57ec992c2af3e5157"

DEPENDS_MX8:mx8 =     "tim-vx"
DEPENDS_MX8:mx8mm =   ""
DEPENDS_MX8:mx8mnul = ""
DEPENDS =              "zlib unzip-native python3 python3-numpy-native python3-pip-native python3-wheel-native python3-pybind11-native tensorflow-protobuf jpeg ${DEPENDS_MX8}"


TENSORFLOW_LITE_SRC ?= "git://source.codeaurora.org/external/imx/tensorflow-imx.git;protocol=https"
SRCBRANCH = "lf-5.10.y_2.0.0"

SRC_URI = "${TENSORFLOW_LITE_SRC};branch=${SRCBRANCH}"
SRCREV = "1c1ed6663a73d5d47a5a96e0e9fa85d2db280954"

SRC_URI += "https://storage.googleapis.com/download.tensorflow.org/models/mobilenet_v1_2018_08_02/mobilenet_v1_1.0_224_quant.tgz;name=model-mobv1"
SRC_URI[model-mobv1.md5sum] = "36af340c00e60291931cb30ce32d4e86"
SRC_URI[model-mobv1.sha256sum] = "d32432d28673a936b2d6281ab0600c71cf7226dfe4cdcef3012555f691744166"

inherit python3native cmake

S = "${WORKDIR}/git"

# Set the CMAKE_SYSROOT, as it is not set in CMAKE_TOOLCHAIN_FILE
EXTRA_OECMAKE_MX8:mx8 =       " -DTFLITE_ENABLE_VX=off  -DTIM_VX_INSTALL=${STAGING_DIR_HOST}/usr "
EXTRA_OECMAKE_MX8:mx8mm =     ""
EXTRA_OECMAKE_MX8:mx8mnul =   ""
EXTRA_OECMAKE = "-DCMAKE_SYSROOT=${PKG_CONFIG_SYSROOT_DIR=}"
EXTRA_OECMAKE += "-DTFLITE_ENABLE_XNNPACK=on -DTFLITE_ENABLE_RUY=on -DTFLITE_ENABLE_NNAPI=on ${EXTRA_OECMAKE_MX8} -DTFLITE_BUILD_EVALTOOLS=on -DTFLITE_BUILD_SHARED_LIB=on  ${S}/tensorflow/lite/"

CXXFLAGS += "-fPIC"

do_configure:prepend(){
    export HTTP_PROXY=${http_proxy}
    export HTTPS_PROXY=${https_proxy}
    export http_proxy=${http_proxy}
    export https_proxy=${https_proxy}

   ${S}/tensorflow/lite/tools/make/download_dependencies.sh
}


do_compile:append () {
    # build pip package
    export PYTHONPATH="${STAGING_LIBDIR_NATIVE}/${PYTHON_DIR}/site-packages"
    export PIP_BUILD_ROOT="${WORKDIR}"
    export TENSORFLOW_TARGET="${TARGET_ARCH}"
    export LDSHARED="${CXX} -shared "
    ${S}/tensorflow/lite/tools/pip_package/build_pip_package.sh
}

do_install() {
    # install libraries
    install -d ${D}${libdir}
    for lib in ${B}/*.so*
    do
        cp --no-preserve=ownership -d $lib ${D}${libdir}
    done

    # install header files
    install -d ${D}${includedir}/tensorflow/lite
    cd ${S}/tensorflow/lite
    cp --parents \
        $(find . -name "*.h*") \
        ${D}${includedir}/tensorflow/lite

    # install version.h from core
    install -d ${D}${includedir}/tensorflow/core/public
    cp ${S}/tensorflow/core/public/version.h ${D}${includedir}/tensorflow/core/public

    # install examples
    install -d ${D}${bindir}/${PN}-${PV}/examples
    install -m 0555 ${B}/label_image ${D}${bindir}/${PN}-${PV}/examples
    install -m 0555 ${B}/benchmark_model ${D}${bindir}/${PN}-${PV}/examples
    install -m 0555 ${B}/coco_object_detection_run_eval ${D}${bindir}/${PN}-${PV}/examples
    install -m 0555 ${B}/imagenet_image_classification_run_eval ${D}${bindir}/${PN}-${PV}/examples
    install -m 0555 ${B}/inference_diff_run_eval ${D}${bindir}/${PN}-${PV}/examples

    # install label_image data
    cp ${S}/tensorflow/lite/examples/label_image/testdata/grace_hopper.bmp ${D}${bindir}/${PN}-${PV}/examples
    cp ${S}/tensorflow/lite/java/ovic/src/testdata/labels.txt ${D}${bindir}/${PN}-${PV}/examples


    # Install python example
    cp ${S}/tensorflow/lite/examples/python/label_image.py ${D}${bindir}/${PN}-${PV}/examples

    # Install mobilenet tflite file
    cp ${WORKDIR}/mobilenet_*.tflite ${D}${bindir}/${PN}-${PV}/examples

    # Install pip package
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}
    ${STAGING_BINDIR_NATIVE}/pip3 install --disable-pip-version-check -v \
        -t ${D}/${PYTHON_SITEPACKAGES_DIR} --no-cache-dir --no-deps \
        ${WORKDIR}/tflite_pip/dist/tflite_runtime-*.whl
}

RDEPENDS_MX8       = ""
RDEPENDS_MX8:mx8   = "libnn-imx nn-imx"
RDEPENDS_MX8:mx8mm = ""
RDEPENDS_MX8:mx8mnul = ""
RDEPENDS:${PN}   = " \
    flatbuffers \
    python3 \
    python3-numpy \
    ${RDEPENDS_MX8} \
"
# TensorFlow and TensorFlow Lite both exports few files, suppres the error
# SSTATE_DUPWHITELIST = "${D}${includedir}"
SSTATE_DUPWHITELIST = "/"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP:${PN} += " \
    already-stripped \
    staticdev \
"

FILES:${PN} += "${libdir}/python*"

COMPATIBLE_MACHINE = "(mx8)"
