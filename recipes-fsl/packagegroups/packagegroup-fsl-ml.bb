DESCRIPTION = "Add packages for AI/ML build"

inherit packagegroup

ML_PKGS    ?= ""
ML_PKGS:mx8 = " \
    armnn \
    armnn-swig \
    onnxruntime \
    pytorch \
    tensorflow-lite \
    torchvision \
"
ML_PKGS:mx8mq = " \
    deepview-rt \
    tvm \
"
ML_PKGS:mx8mp = " \
    deepview-rt \
    tvm \
"
ML_PKGS:mx8mm = " \
    eiq-apps \
"
ML_PKGS:mx8mp = " \
    eiq-apps \
"
RDEPENDS:${PN} = " \
    ${ML_PKGS} \
"
