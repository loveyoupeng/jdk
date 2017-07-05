/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 8047290
 * @summary Ensure that Monitor::lock does not assert when it correctly acquires a lock which must always have safepoint checks.
 * @library /testlibrary /../../test/lib
 * @build AssertSafepointCheckConsistency4
 * @run main ClassFileInstaller sun.hotspot.WhiteBox
 *                              sun.hotspot.WhiteBox$WhiteBoxPermission
 * @run main AssertSafepointCheckConsistency4
 */

import com.oracle.java.testlibrary.*;

import sun.hotspot.WhiteBox;

public class AssertSafepointCheckConsistency4 {
    public static void main(String args[]) throws Exception {
        if (args.length > 0) {
            WhiteBox.getWhiteBox().assertMatchingSafepointCalls(true, false);
        }
        if (Platform.isDebugBuild()){
            ProcessBuilder pb = ProcessTools.createJavaProcessBuilder(
               "-Xbootclasspath/a:.",
               "-XX:+UnlockDiagnosticVMOptions",
               "-XX:+WhiteBoxAPI",
               "-XX:-TransmitErrorReport",
               "-XX:-CreateMinidumpOnCrash",
               "-Xmx32m",
               "AssertSafepointCheckConsistency4",
               "test");

            OutputAnalyzer output = new OutputAnalyzer(pb.start());
            output.shouldNotContain("assert");
            output.shouldNotContain("never");
            output.shouldNotContain("always");
        }
    }
}


