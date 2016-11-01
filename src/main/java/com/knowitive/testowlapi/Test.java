package com.knowitive.testowlapi;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class Test {

	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException {
		// TODO Auto-generated method stub

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		String base = "http://www.semanticweb.org/ontologies/individualsexample";
		OWLOntology ont = man.createOntology(IRI.create(base));
		OWLDataFactory dataFactory = man.getOWLDataFactory();
	       // In this case, we would like to state that matthew has a father who is
        // peter. We need a subject and object - matthew is the subject and
        // peter is the object. We use the data factory to obtain references to
        // these individuals
        OWLIndividual matthew = dataFactory.getOWLNamedIndividual(IRI.create(base + "#matthew"));
        OWLIndividual peter = dataFactory.getOWLNamedIndividual(IRI.create(base + "#peter"));
        // We want to link the subject and object with the hasFather property,
        // so use the data factory to obtain a reference to this object
        // property.
        OWLObjectProperty hasFather = dataFactory.getOWLObjectProperty(IRI.create(base + "#hasFather"));
        // Now create the actual assertion (triple), as an object property
        // assertion axiom matthew --> hasFather --> peter
        OWLObjectPropertyAssertionAxiom assertion = dataFactory.getOWLObjectPropertyAssertionAxiom(hasFather, matthew,
            peter);
        // Finally, add the axiom to our ontology and save
        AddAxiom addAxiomChange = new AddAxiom(ont, assertion);
        man.applyChange(addAxiomChange);
        // We can also specify that matthew is an instance of Person. To do this
        // we use a ClassAssertion axiom. First we need a reference to the
        // person class
        OWLClass personClass = dataFactory.getOWLClass(IRI.create(base + "#Person"));
        // Now we will create out Class Assertion to specify that matthew is an
        // instance of Person (or rather that Person has matthew as an instance)
        OWLClassAssertionAxiom ax = dataFactory.getOWLClassAssertionAxiom(personClass, matthew);
        // Add this axiom to our ontology. We can use a short cut method -
        // instead of creating the AddAxiom change ourselves, it will be created
        // automatically and the change applied
        man.addAxiom(ont, ax);
        // Save our ontology
        man.saveOntology(ont, IRI.create("file:/temp/example.owl"));
	}

}
