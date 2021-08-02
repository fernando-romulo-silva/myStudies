package br.com.fernando.chapter09_working_with_the_kubernets_api.part01_api_versioning;

public class Part03_Stable {

	// The stable level of the API is a tested, production-ready software. 
	// The version name in the stable API will be vX where X is an integer number, such as v1 for example.
	//
	// Kubernetes API utilizes a concept of API groups. 
	// API groups have been introduced to make it easier to extend the Kubernetes API in the future. 
	// The API group is specified in a REST path and in the apiVersion field of a call's JSON payload. 
	// Currently, there are several API groups in use: core, batch, and extensions. 
	// The group name is a part of the REST path of an API call: /apis/$GROUP_NAME/$VERSION. 
	// The core group is an exception, it does not show up in the REST path, for example: /api/v1. 
}
