// 1. Project Metadata
// This usually matches the folder name
name := "onnx-lab"

version := "0.1.0"

// 2. The Scala Version
// llm4s uses Scala 3. Let's use a stable version.
scalaVersion := "3.3.3"

// 3. The Dependencies (The 'pip install' part)
// libraryDependencies is a List of libraries.
// The syntax is: "GroupID" % "ArtifactID" % "Version"
libraryDependencies += (
  // YOUR TASK: Find the correct line for ONNX Runtime.
  // Go to: https://mvnrepository.com/artifact/com.microsoft.onnxruntime/onnxruntime
  // Look for version "1.17.1" (or similar).
  // Click the "SBT" tab on that page to copy the line.
  
  // Paste it here:
    "com.microsoft.onnxruntime" % "onnxruntime" % "1.17.1"
)