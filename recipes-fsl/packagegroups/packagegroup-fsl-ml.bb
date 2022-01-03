DESCRIPTION = "Add packages for AI/ML build"

inherit packagegroup

ML_PKGS    ?= ""
ML_PKGS_mx8 = " \
    armnn \
    armnn-swig \
    onnxruntime \
    pytorch \
    tensorflow-lite \
    torchvision \
"
ML_PKGS_mx8mq = " \
    deepview-rt \
    tvm \
"
ML_PKGS_mx8mp = " \
    deepview-rt \
    tvm \
"
ML_PKGS_mx8mm = " \
    eiq-apps \
"
ML_PKGS_mx8mp = " \
    eiq-apps \
"
RDEPENDS_${PN} = " \
    ${ML_PKGS} \
"
