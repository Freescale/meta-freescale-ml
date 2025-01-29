SUMMARY = "Tensor Interface Module for OpenVX"
DESCRIPTION = "TIM-VX is a software integration module provided by VeriSilicon \
to facilitate deployment of Neural-Networks on OpenVX enabled ML accelerators. It serves as \
the backend binding for runtime frameworks such as Android NN, Tensorflow-Lite, MLIR, TVM and more."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a223f028c6a73fd2bd9223b79a38dcf6"

DEPENDS = "imx-gpu-viv"

SRC_URI = "${TIM_VX_SRC};branch=${SRCBRANCH}"
TIM_VX_SRC ?= "git://github.com/nxp-imx/tim-vx-imx.git;protocol=https"
SRCBRANCH = "lf-6.6.52_2.2.0"
SRCREV = "8c5bf6150903e14275b5c6b6b63a7035ce60dde1" 

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE =  " \
    -DCONFIG=YOCTO \
    -DCMAKE_SYSROOT=${PKG_CONFIG_SYSROOT_DIR} \
    -DTIM_VX_ENABLE_TEST=off \
    -DTIM_VX_USE_EXTERNAL_OVXLIB=off \
"

# Output library is unversioned
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

COMPATIBLE_MACHINE          = "(^$)"
COMPATIBLE_MACHINE:imxgpu3d = "(mx8-nxp-bsp)"
COMPATIBLE_MACHINE:mx8mm-nxp-bsp    = "(^$)"
