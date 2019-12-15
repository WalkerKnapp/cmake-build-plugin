plugins {
	id `java-gradle-plugin`
	id `maven-publish`
}

group = "com.walker.plugins"
version = "1.0"

gradlePlugin {
	(plugins) {
		register("cmakeLibrary") {
			id = "com.github.WalkerKnapp.cmake-build-plugin"
			implementationClass = "com.walker.plugins.cmake.CMakeLibraryPlugin"
		}
		register("wrappedLibrary") {
			id = "com.walker.plugins.wrapped-native"
			implementationClass = "com.walker.plugins.cmake.WrappedNativeLibraryPlugin"
		}
	}
}