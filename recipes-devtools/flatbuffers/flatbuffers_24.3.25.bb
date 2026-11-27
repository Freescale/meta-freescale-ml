SUMMARY = "Memory Efficient Serialization Library"
DESCRIPTION = "FlatBuffers is a cross platform serialization library architected for maximum memory efficiency. Serialized data is accessed directly, without parsing or unpacking it first, while keeping forwards and backwards compatibility."
HOMEPAGE = "https://github.com/google/flatbuffers"
SECTION = "console/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "flatbuffers-native"

SRC_URI = "git://github.com/google/flatbuffers.git;branch=master;protocol=https"
SRCREV = "e6463926479bd6b330cbcf673f7e917803fd5831"

CVE_PRODUCT = "google:flatbuffers"

EXTRA_OECMAKE += "\
    -DFLATBUFFERS_BUILD_TESTS=OFF \
    -DFLATBUFFERS_BUILD_SHAREDLIB=ON \
"
EXTRA_OECMAKE:append:class-target = " -DFLATBUFFERS_FLATC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/flatc"

inherit cmake python3native

rm_flatc_cmaketarget_for_target() {
    rm -f "${SYSROOT_DESTDIR}/${libdir}/cmake/flatbuffers/FlatcTargets.cmake"
}
rm_flatc_cmaketarget_for_target[doc] = "Drops FlatcTargets.cmake from the target sysroot: it expects a flatc binary there, which is never staged for the target"
SYSROOT_PREPROCESS_FUNCS:append:class-target = " rm_flatc_cmaketarget_for_target"

PACKAGE_BEFORE_PN = "${PN}-compiler"

FILES:${PN}-compiler = "${bindir}"

RDEPENDS:${PN}-compiler = "${PN}"
RDEPENDS:${PN}-dev += "${PN}-compiler"

BBCLASSEXTEND = "native nativesdk"
