package fr.umlv.papayadb.client;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Main {
	
	static void displayApplicationMenu() {
		System.out.println("*** Welcome on the PapayaDB Application ***");
		System.out.println("We present to you our features :");
		System.out.println("#####################################################");
		System.out.println("#  Note that your request will be case sensitive !  #");
		System.out.println("#####################################################");
		System.out.println(
				"To insert a new document into a database please enter : insert pathOfTheDocument");
		System.out.println(
				"To select a specific document please enter : find documentId");
		System.out.println(
				"To delete a specific document please enter : delete documentID");
	}
	
	static void dispatchRequest(String RequestType,String RequestBody, Client c) throws Exception {
		switch (RequestType) {
		case "insert":
			List<ObjectNode> documents = new LinkedList<ObjectNode>();
			documents = Client.parseFile(RequestBody);
			for (ObjectNode objectNode : documents) {
				c.insertJson(objectNode);
			}
			break;
		case "find":
			ObjectMapper findMapper = new ObjectMapper();
			ObjectNode findNode = findMapper.createObjectNode();
			findNode.put("_id", RequestBody);
			c.findById(findNode);
			break;
		case "delete":
			ObjectMapper deleteMapper = new ObjectMapper();
			ObjectNode deleteNode = deleteMapper.createObjectNode();
			deleteNode.put("_id", RequestBody);
			c.deleteDocument(deleteNode);
			break;
		default:
			System.out.println("Sorry we cannot answer your request");
			displayApplicationMenu();
			break;
		}
	}

	public static void main(String[] args) {
		try {
			Client c = new Client();
			displayApplicationMenu();
			Scanner requestScanner = new Scanner(System.in);
			System.out.println("Enter a choice please :");
			String request = requestScanner.nextLine();
			while (!request.contentEquals("quit")) {
				String[] splitedRequest = request.split(" "); 
				dispatchRequest(splitedRequest[0],splitedRequest[1],c);
				System.out.println("Enter a choice please :");
				request = requestScanner.nextLine();
				if (request.contentEquals("menu")) {
					Runtime.getRuntime().exec("clear");
					displayApplicationMenu();
					System.out.println("Enter a choice please :");
					request = requestScanner.nextLine();
				}
			}
			requestScanner.close();
			System.out.println("The job is done!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
