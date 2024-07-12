package dev.aga.model.entity

import org.komapper.annotation.KomapperAutoIncrement
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperIgnore
import org.komapper.annotation.KomapperManyToOne
import org.komapper.annotation.KomapperOneToMany
import org.komapper.annotation.KomapperTable
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.query.EntityStore

@KomapperTable("groups")
@KomapperEntity
@KomapperManyToOne(targetEntity = Parent::class, navigator = "parent")
@KomapperOneToMany(targetEntity = Child::class, navigator = "children")
data class Group(
  @KomapperAutoIncrement @KomapperId val id: Long? = null,
  val parentId: Long,
  @KomapperIgnore private val store: EntityStore? = null,
) {
  val children: List<Child>? by lazy {
    store?.oneToManyById(Meta.group, Meta.child)?.get(id)?.map { it.copy(store = store) }
  }
}
