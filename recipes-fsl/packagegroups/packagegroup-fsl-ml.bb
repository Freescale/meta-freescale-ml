DESCRIPTION = "Add packages for AI/ML build"

inherit packagegroup

ML_PKGS    ?= ""
ML_PKGS:mx8-nxp-bsp = " \
    nnstreamer \
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
RDEPENDS:${PN} = " \
    ${ML_PKGS} \
"
