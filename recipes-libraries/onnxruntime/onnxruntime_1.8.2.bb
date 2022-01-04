# Copyright 2020-2021
DESCRIPTION = "cross-platform, high performance scoring engine for ML models"
SECTION = "devel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f7e3b1308cb5c00b372a6e78835732d"

DEPENDS = "libpng zlib ${BPN}-native"

ONNXRUNTIME_SRC ?= "gitsm://source.codeaurora.org/external/imx/onnxruntime-imx.git;protocol=https"
SRCBRANCH = "lf-5.10.72_2.2.0"

SRCREV = "d5d8898338f8713d09ff0e85b6ddbe464138baf7"

SRC_URI = "${ONNXRUNTIME_SRC};branch=${SRCBRANCH}"

# Squeezenet sample model
SRC_URI += "https://github.com/onnx/models/raw/6ab957a2fe61f34a76c670946f7cbd806d2cacca/vision/classification/squeezenet/model/squeezenet1.0-9.tar.gz;name=squeezenet-model"
SRC_URI[squeezenet-model.md5sum] = "92e240a948f9bbc92534d752eb465317"
SRC_URI[squeezenet-model.sha256sum] = "f4c9a2906a949f089bee5ef1bf9ea1c0dc1b49d5abeb1874fff3d206751d0f3b"
SRC_URI += "https://github.com/onnx/models/raw/6ab957a2fe61f34a76c670946f7cbd806d2cacca/LICENSE;name=squeezenet-license"
SRC_URI[squeezenet-license.md5sum] = "3b83ef96387f14655fc854ddc3c6bd57"
SRC_URI[squeezenet-license.sha256sum] = "cfc7749b96f63bd31c3c42b5c471bf756814053e847c10f3eb003417bc523d30"


S = "${WORKDIR}/git"

inherit cmake python3native

OECMAKE_SOURCEPATH = "${S}/cmake"
OECMAKE_GENERATOR = "Unix Makefiles"

# Notes:
# Protobuff/Protoc: 
#   - protobuf is essetially built twice for native and target system
#   - DONNX_CUSTOM_PROTOC_EXECUTABLE  - use native protoc
#   - onnxruntime_USE_PREBUILT_PB=OFF - we still need protobuf compiled from target system; although we already have native version
# Eigen: 
#   - the git operation within CMake fails, so we treat it as 'pre-installed' although it's fetched during fetch phase
#   - the eigen_SOURCE_PATH needs to match 'destsuffix' in SRC_URI for eigen

EXTRA_OECMAKE += "\
-DONNX_CUSTOM_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/${PN}-native/protoc \
-DCMAKE_BUILD_TYPE=RelWithDebInfo \
"

PYTHON_DEPENDS = "${PYTHON_PN}-native ${PYTHON_PN}-pip-native ${PYTHON_PN}-wheel-native ${PYTHON_PN}-setuptools-native ${PYTHON_PN}-numpy-native"
PYTHON_RDEPENDS = "${PYTHON_PN} ${PYTHON_PN}-numpy ${PYTHON_PN}-protobuf flatbuffers-${PYTHON_PN}"

PACKAGECONFIG_VSI_NPU       = ""
PACKAGECONFIG_VSI_NPU_mx8   = "vsi_npu"
PACKAGECONFIG_VSI_NPU_mx8mm = ""
PACKAGECONFIG_VSI_NPU_mx8mnul = ""
PACKAGECONFIG_VSI_NPU_mx8mpul = ""
PACKAGECONFIG_VSI_NPU_mx8ulp = ""

PACKAGECONFIG ?= "openmp reports sharedlib armnn eigenblas acl acl-2108 nnapi python ${PACKAGECONFIG_VSI_NPU}"

PACKAGECONFIG[nsync] = "-Donnxruntime_USE_NSYNC=ON, -Donnxruntime_USE_NSYNC=OFF"
PACKAGECONFIG[prebuilt] = "-Donnxruntime_USE_PREBUILT_PB=ON, -Donnxruntime_USE_PREBUILT_PB=OFF"
PACKAGECONFIG[openmp] = "-Donnxruntime_USE_OPENMP=ON, -Donnxruntime_USE_OPENMP=OFF"
PACKAGECONFIG[trt] = "-Donnxruntime_USE_TRT=ON, -Donnxruntime_USE_TRT=OFF"
PACKAGECONFIG[nuphar] = "-Donnxruntime_USE_NUPHAR=ON, -Donnxruntime_USE_NUPHAR=OFF"
PACKAGECONFIG[brainslice] = "-Donnxruntime_USE_BRAINSLICE=ON, -Donnxruntime_USE_BRAINSLICE=OFF"
PACKAGECONFIG[python] = "-Donnxruntime_ENABLE_PYTHON=ON, -Donnxruntime_ENABLE_PYTHON=OFF, ${PYTHON_DEPENDS}, ${PYTHON_RDEPENDS}"
PACKAGECONFIG[sharedlib] = "-Donnxruntime_BUILD_SHARED_LIB=ON, -Donnxruntime_BUILD_SHARED_LIB=OFF"
PACKAGECONFIG[eigenblas] = "-Donnxruntime_USE_EIGEN_FOR_BLAS=ON, -Donnxruntime_USE_EIGEN_FOR_BLAS=OFF"
PACKAGECONFIG[openblas] = "-Donnxruntime_USE_OPENBLAS=ON, -Donnxruntime_USE_OPENBLAS=OFF"
PACKAGECONFIG[dnnl] = "-Donnxruntime_USE_DNNL=ON, -Donnxruntime_USE_DNNL=OFF"
PACKAGECONFIG[mklml] = "-Donnxruntime_USE_MKLML=ON, -Donnxruntime_USE_MKLML=OFF"
PACKAGECONFIG[gemmlowp] = "-Donnxruntime_USE_GEMMLOWP=ON, -Donnxruntime_USE_GEMMLOWP=OFF"
PACKAGECONFIG[ngraph] = "-Donnxruntime_USE_NGRAPH=ON, -Donnxruntime_USE_NGRAPH=OFF"
PACKAGECONFIG[openvino] = "-Donnxruntime_USE_OPENVINO=ON, -Donnxruntime_USE_OPENVINO=OFF"
PACKAGECONFIG[interop] = "-Donnxruntime_ENABLE_LANGUAGE_INTEROP_OPS=ON, -Donnxruntime_ENABLE_LANGUAGE_INTEROP_OPS=OFF"
PACKAGECONFIG[dml] = "-Donnxruntime_USE_DML=ON, -Donnxruntime_USE_DML=OFF"
PACKAGECONFIG[telemetry] = "-Donnxruntime_USE_TELEMETRY=ON, -Donnxruntime_USE_TELEMETRY=OFF"
PACKAGECONFIG[armnn-relu] = "-Donnxruntime_ARMNN_RELU_USE_CPU=ON, -Donnxruntime_ARMNN_RELU_USE_CPU=OFF"
PACKAGECONFIG[armnn-bn] = "-Donnxruntime_ARMNN_BN_USE_CPU=ON, -Donnxruntime_ARMNN_BN_USE_CPU=OFF"
PACKAGECONFIG[opschema] = "-Donnxruntime_PYBIND_EXPORT_OPSCHEMA=ON, -Donnxruntime_PYBIND_EXPORT_OPSCHEMA=OFF"
PACKAGECONFIG[nnapi] = "-Donnxruntime_USE_NNAPI_BUILTIN=ON, -Donnxruntime_USE_NNAPI_BUILTIN=OFF"
PACKAGECONFIG[tvm] = "-Donnxruntime_USE_TVM=ON, -Donnxruntime_USE_TVM=OFF"
PACKAGECONFIG[llvm] = "-Donnxruntime_USE_LLVM=ON, -Donnxruntime_USE_LLVM=OFF"
PACKAGECONFIG[microsoft] = "-Donnxruntime_ENABLE_MICROSOFT_INTERNAL=ON, -Donnxruntime_ENABLE_MICROSOFT_INTERNAL=OFF"
PACKAGECONFIG[eigenthreadpool] = "-Donnxruntime_USE_EIGEN_THREADPOOL=ON, -Donnxruntime_USE_EIGEN_THREADPOOL=OFF"
PACKAGECONFIG[tensorrt] = "-Donnxruntime_USE_TENSORRT=ON, -Donnxruntime_USE_TENSORRT=OFF"
PACKAGECONFIG[crosscompiling] = "-Donnxruntime_CROSS_COMPILING=ON, -Donnxruntime_CROSS_COMPILING=OFF "
PACKAGECONFIG[server] = "-Donnxruntime_BUILD_SERVER=ON, -Donnxruntime_BUILD_SERVER=OFF"
PACKAGECONFIG[x86] = "-Donnxruntime_BUILD_x86=ON, -Donnxruntime_BUILD_x86=OFF"
PACKAGECONFIG[fullprotobuf] = "-Donnxruntime_USE_FULL_PROTOBUF=ON, -Donnxruntime_USE_FULL_PROTOBUF=OFF"
PACKAGECONFIG[ops] = "-Donnxruntime_DISABLE_CONTRIB_OPS=ON, -Donnxruntime_DISABLE_CONTRIB_OPS=OFF"
PACKAGECONFIG[staticruntime] = "-Donnxruntime_MSVC_STATIC_RUNTIME=ON, -Donnxruntime_MSVC_STATIC_RUNTIME=OFF"
PACKAGECONFIG[runtests] = "-Donnxruntime_RUN_ONNX_TESTS=ON, -Donnxruntime_RUN_ONNX_TESTS=OFF"
PACKAGECONFIG[reports] = "-Donnxruntime_GENERATE_TEST_REPORTS=ON, -Donnxruntime_GENERATE_TEST_REPORTS=OFF"
PACKAGECONFIG[devmode] = "-Donnxruntime_DEV_MODE=ON, -Donnxruntime_DEV_MODE=OFF"
PACKAGECONFIG[cuda] = "-Donnxruntime_USE_CUDA=ON, -Donnxruntime_USE_CUDA=OFF"
PACKAGECONFIG[automl] = "-Donnxruntime_USE_AUTOML=ON, -Donnxruntime_USE_AUTOML=OFF"
PACKAGECONFIG[jemalloc] = "-Donnxruntime_USE_JEMALLOC=ON, -Donnxruntime_USE_JEMALLOC=OFF"
PACKAGECONFIG[mimalloc] = "-Donnxruntime_USE_MIMALLOC=ON, -Donnxruntime_USE_MIMALLOC=OFF"
PACKAGECONFIG[csharp] = "-Donnxruntime_BUILD_CSHARP=ON, -Donnxruntime_BUILD_CSHARP=OFF"
PACKAGECONFIG[java] = "-Donnxruntime_BUILD_JAVA=ON, -Donnxruntime_BUILD_JAVA=OFF"

PACKAGECONFIG[armnn] = "-Donnxruntime_USE_ARMNN=ON, -Donnxruntime_USE_ARMNN=OFF, armnn"
PACKAGECONFIG[acl] = "-Donnxruntime_USE_ACL=ON, -Donnxruntime_USE_ACL=OFF, arm-compute-library, arm-compute-library"
PACKAGECONFIG[acl-1908] = "-Donnxruntime_USE_ACL_1908=ON, -Donnxruntime_USE_ACL_1908=OFF, arm-compute-library"
PACKAGECONFIG[acl-2002] = "-Donnxruntime_USE_ACL_2002=ON, -Donnxruntime_USE_ACL_2002=OFF, arm-compute-library"
PACKAGECONFIG[acl-2008] = "-Donnxruntime_USE_ACL_2008=ON, -Donnxruntime_USE_ACL_2008=OFF, arm-compute-library"
PACKAGECONFIG[acl-2102] = "-Donnxruntime_USE_ACL_2102=ON, -Donnxruntime_USE_ACL_2102=OFF, arm-compute-library"
PACKAGECONFIG[acl-2108] = "-Donnxruntime_USE_ACL_2108=ON, -Donnxruntime_USE_ACL_2108=OFF, arm-compute-library"
PACKAGECONFIG[vsi_npu] = "-Donnxruntime_USE_VSI_NPU=ON -Donnxruntime_OVXLIB_INCLUDE=${STAGING_INCDIR}/OVXLIB, -Donnxruntime_USE_VSI_NPU=OFF, nn-imx"

do_compile_prepend() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'python', 'true', 'false', d)}; then
        # required to pull pybind11
        export HTTP_PROXY=${http_proxy}
        export HTTPS_PROXY=${https_proxy}
        export http_proxy=${http_proxy}
        export https_proxy=${https_proxy}
    fi
}

do_compile_append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'python', 'true', 'false', d)}; then
        cd ${WORKDIR}/build
        ${PYTHON} ${S}/setup.py bdist_wheel
    fi
}

do_install_append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"
    
    # copy extracted squeezenet tarball and add Apache2 license
    cp $CP_ARGS ${WORKDIR}/squeezenet ${D}${bindir}/${BP}
    install -m 0644 ${WORKDIR}/LICENSE ${D}${bindir}/${BP}/squeezenet

    if ${@bb.utils.contains('PACKAGECONFIG', 'python', 'true', 'false', d)}; then
        export PIP_DISABLE_PIP_VERSION_CHECK=1
        export PIP_NO_CACHE_DIR=1
        install -d ${D}/${PYTHON_SITEPACKAGES_DIR}
        ${STAGING_BINDIR_NATIVE}/pip3 install -v \
            -t ${D}/${PYTHON_SITEPACKAGES_DIR} --no-deps \
            ${WORKDIR}/build/dist/onnxruntime-*.whl
        find ${D}/${PYTHON_SITEPACKAGES_DIR} -type d -name "__pycache__" -exec rm -Rf {} +
    fi
}

# libonnxruntime_providers_shared.so is being packaged into -dev which is intended
INSANE_SKIP_${PN}-dev += "dev-elf"

# a separate tests package for the test binaries not appearing in the main package
PACKAGE_BEFORE_PN = "${PN}-tests"
FILES_${PN}-tests = "${bindir}/${BP}/tests/*"
FILES_${PN} += "${PYTHON_SITEPACKAGES_DIR}"
FILES_${PN} += "${bindir}/${BP}/squeezenet"

# libcustom_op_library.so is in bindir, which is intended;
# onnxruntime_shared_lib_test requires the shlib to be in the same directory as testdata to run properly
INSANE_SKIP_${PN}-tests += "libdir"
INSANE_SKIP_${PN}-dbg += "libdir"

RDEPENDS_${PN}-tests += "arm-compute-library"

