package dev.aga

import dev.aga.model.entity.Child
import dev.aga.model.entity.Group
import dev.aga.model.entity.Parent
import dev.aga.model.entity.child
import dev.aga.model.entity.group
import dev.aga.model.entity.parent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.jdbc.JdbcDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RelationshipTest(@Autowired private val db: JdbcDatabase) {

  @Test
  fun `test relationships`() {
    val parent = db.runQuery {
      QueryDsl.insert(Meta.parent).single(Parent(name = "test")).returning()
    }
    val group1 = db.runQuery {
      QueryDsl.insert(Meta.group).single(Group(parentId = parent.id!!))
    }

    val group2 = db.runQuery {
      QueryDsl.insert(Meta.group).single(Group(parentId = parent.id!!))
    }

    db.runQuery {
      QueryDsl.insert(Meta.child).single(Child(parentId = parent.id!!, groupId = group1.id!!))
    }

    db.runQuery {
      QueryDsl.insert(Meta.child).single(Child(parentId = parent.id!!, groupId = group2.id!!))
    }

    val fetchedParent = db.runQuery {
      QueryDsl.from(Meta.parent)
        .innerJoin(Meta.child) { Meta.parent.id eq Meta.child.parentId }
        .innerJoin(Meta.group) { Meta.parent.id eq Meta.group.parentId }
        .where { Meta.parent.id eq parent.id }
        .includeAll()
    }.let { it[Meta.parent].firstOrNull()?.copy(store = it) }

    // correctly returns both groups
    assertThat(fetchedParent?.groups).hasSize(2)
    // get just group1
    val g1 = fetchedParent?.groups?.first { it.id == group1.id}
    // this should only contain children where group_id == group1's id?
    assertThat(g1?.children).hasSize(1).extracting("id").isEqualTo(group1.id)

    val g2 = fetchedParent?.groups?.first { it.id == group2.id }
    // this should only contain children where group_id == group1's id?
    assertThat(g2?.children).hasSize(1).extracting("id").isEqualTo(group2.id)
  }
}
