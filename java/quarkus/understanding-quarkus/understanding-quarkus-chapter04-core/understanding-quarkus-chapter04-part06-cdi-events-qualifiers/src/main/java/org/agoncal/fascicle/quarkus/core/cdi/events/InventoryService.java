package org.agoncal.fascicle.quarkus.core.cdi.events;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */

@Singleton
public class InventoryService {

  @Inject
  Logger logger;

  List<Book> inventory = new ArrayList<>();

  public void addBook(@Observes @Added Book book) {
    logger.info("Adding book " + book.getTitle() + " to inventory");
    inventory.add(book);
  }

  public void removeBook(@Observes @Removed Book book) {
    logger.info("Removing book " + book.getTitle() + " to inventory");
    inventory.remove(book);
  }
}

