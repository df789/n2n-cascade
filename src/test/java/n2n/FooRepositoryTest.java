/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package n2n;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FooRepositoryTest {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private FooRepository fooRepository;

	@Autowired
	private BarRepository barRepository;

	@Test
	public void testFindByLastName() {
		// set up

		Foo foo = new Foo();
		Bar bar = new Bar();
		foo = fooRepository.save(foo);
		bar = barRepository.save(bar);
		long fooId = foo.getId();
		long barId = bar.getId();
		entityManager.flush();
		entityManager.clear();

		System.out.println("Adding....");
		foo = fooRepository.findById(fooId).get();
		bar = barRepository.findById(barId).get();
		foo.addBar(bar);
		// This save() was causing the flush() to fail with:
		// - javax.persistence.EntityExistsException: A different object with the same identifier value was already associated with the session : [hello.n2n.FooBar#hello.n2n.FooBarId@400]
		//barRepository.save(bar);
		entityManager.flush();
		entityManager.clear();

		// exercise

		System.out.println("Removing....");
		foo = fooRepository.findById(fooId).get();
		bar = barRepository.findById(barId).get();
		foo.removeBar(bar);
		barRepository.save(bar);
		entityManager.flush();
		entityManager.clear();

		// verify

		foo = fooRepository.findById(fooId).get();
		bar = barRepository.findById(barId).get();
		assertTrue("Should be no foo.getFooBars(): " + foo.getFooBars(),
				foo.getFooBars().isEmpty());
		assertTrue("Should be no bar.getFooBars(): " + bar.getFooBars(),
				bar.getFooBars().isEmpty());
	}
}