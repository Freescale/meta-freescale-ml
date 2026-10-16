# Without CMAKE_BUILD_TYPE, abseil keeps asserts using uintptr_t, which gcc 16
# headers no longer provide; a Release build would change the generated code.
CXXFLAGS:append = " -include cstdint"
