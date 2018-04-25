package org.gradle.profiler;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BazelVersionInspector {

    private BazelVersion cachedBazelVersion;
    private String[] commands = {"bazel", "version"};

    public BazelVersion getVersion() {
        if (cachedBazelVersion == null) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(commands);
                processBuilder.redirectError();
                Process process = processBuilder.start();
                process.waitFor();
                String output = IOUtils.toString(process.getInputStream(), "UTF-8");
                // parse the output
                Pattern pattern = Pattern.compile("Build label: (.+)");
                Matcher matcher = pattern.matcher(output);
                if (matcher.find()) {
                    String version = matcher.group(1);
                    cachedBazelVersion = new BazelVersion(version);
                } else {
                    cachedBazelVersion = new BazelVersion("bazel");
                }

            } catch (InterruptedException | IOException e) {
                System.err.println("Unable to obtain the bazel version");
                e.printStackTrace();
                cachedBazelVersion = new BazelVersion("bazel");
            }

        }
        return cachedBazelVersion;
    }
}
