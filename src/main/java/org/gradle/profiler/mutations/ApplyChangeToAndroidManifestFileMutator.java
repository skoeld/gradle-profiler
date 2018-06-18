package org.gradle.profiler.mutations;

import java.io.File;

public class ApplyChangeToAndroidManifestFileMutator extends AbstractFileChangeMutator {
    public ApplyChangeToAndroidManifestFileMutator(File sourceFile) {
        super( sourceFile );
    }

    @Override
    protected void applyChangeTo(StringBuilder text) {
        int insertPos = 0;
        insertPos = text.lastIndexOf("</manifest>");
        if (insertPos > 0) {
            text.insert(insertPos, "<!-- " + getUniqueText() + " --><permission android:name=\"com.acme.SOME_PERMISSION\"/>");
            return;
        }
        insertPos = text.lastIndexOf("/>");
        if (insertPos > 0) {
            text.replace(insertPos, insertPos + 2, " xmlns:android=\"http://schemas.android.com/apk/res/android\"><!-- " + getUniqueText() + " --><permission android:name=\"com.acme.SOME_PERMISSION\"/></manifest>");
            return;
        }
        throw new IllegalArgumentException("Cannot parse android manifest file " + sourceFile + " to apply changes");
    }
}
