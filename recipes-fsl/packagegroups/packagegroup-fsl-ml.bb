DESCRIPTION = "Add packages for AI/ML build"

inherit packagegroup

ML_PKGS    ?= ""
ML_PKGS:mx8-nxp-bsp = " \
    armnn \
    armnn-swig \
    onnxruntime \
    pytorch \
    tensorflow-lite \
    torchvision \
"
ML_PKGS:mx8mq-nxp-bsp = " \
    deepview-rt \
    tvm \
"
ML_PKGS:mx8mp-nxp-bsp = " \
    deepview-rt \
    tvm \
"
ML_PKGS:mx8mm-nxp-bsp = " \
    eiq-apps \
"
ML_PKGS:mx8mp-nxp-bsp = " \
    eiq-apps \
"
RDEPENDS:${PN} = " \
    ${ML_PKGS} \
"
