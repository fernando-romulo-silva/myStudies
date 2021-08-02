package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part13_label;

public class Part01 {

	// To add the metadata to our image, we use the LABEL instruction. 
	// A single label is a key-value pair. 
	// If you need to have spaces in the label value, you will need to wrap it in a pair of quotes.
	//
	// The syntax of the LABEL instruction is straightforward:
	//
	// LABEL "key"="value"
	//
	// To have a multiline value, separate the lines with backslashes; for example:
	//
	// LABEL description="This is my \
	// multiline description of the software."
	//
	// You can have multiple labels in a single image. 
	// Provide them separated with a space or a backslash; for example:
	//
	// LABEL key1="value1" key2="value2" key3="value3"
	// LABEL key1="value1" \
	// key2="value2" \
	// key3="value3"
	//
	// Each LABEL instruction creates a new layer. 
	// If your image has many labels, use the multiple form of the single LABEL instruction.
}
