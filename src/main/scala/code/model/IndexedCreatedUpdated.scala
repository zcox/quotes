package code.model

import net.liftweb.mapper._

/** Ensures that the createdAt and updatedAt fields are indexed. */
trait IndexedCreatedUpdated extends CreatedUpdated {
  self: BaseMapper =>

  override protected def createdAtIndexed_? = true
  override protected def updatedAtIndexed_? = true
}
