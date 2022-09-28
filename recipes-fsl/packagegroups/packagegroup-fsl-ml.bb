DESCRIPTION = "Add packages for AI/ML build"

inherit packagegroup

ML_NNSTREAMER_PKGS_LIST = " \
    nnstreamer \
    nnstreamer-protobuf \
    nnstreamer-python3 \
    nnstreamer-tensorflow-lite \
"
ML_NNSTREAMER_PKGS = ""
ML_NNSTREAMER_PKGS:mx8-nxp-bsp:imxgpu = "${ML_NNSTREAMER_PKGS_LIST}"

ML_PKGS    ?= ""
ML_PKGS:mx8-nxp-bsp = " \
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
    tensorflow-lite-vx-delegate \
    tvm \
"
RDEPENDS:${PN} = " \
    ${ML_PKGS} \
    ${ML_NNSTREAMER_PKGS} \
"
