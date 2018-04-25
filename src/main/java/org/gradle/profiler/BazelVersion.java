package org.gradle.profiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BazelVersion {

    private final String version;

    public BazelVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

}
