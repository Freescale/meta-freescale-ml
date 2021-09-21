DESCRIPTION = "Add packages for AI/ML build"

inherit packagegroup

ML_PKGS    ?= ""
ML_PKGS_mx8 = " \
    armnn \
    armnn-swig \
    deepview-rt \
    eiq-apps \
    onnxruntime \
    pytorch \
    tensorflow-lite \
    torchvision \
    tvm \
"
RDEPENDS_${PN} = " \
    ${ML_PKGS} \
"
