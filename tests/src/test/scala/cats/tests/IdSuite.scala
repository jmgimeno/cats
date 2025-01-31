package cats.tests

import cats.{Bimonad, CommutativeMonad, Id, Reducible, Traverse}
import cats.laws.discipline._
import cats.syntax.applicative._
import cats.syntax.eq._
import org.scalacheck.Prop._

class IdSuite extends CatsSuite {
  implicit val iso: SemigroupalTests.Isomorphisms[Id] =
    SemigroupalTests.Isomorphisms.invariant[Id]

  checkAll("Id[Int]", BimonadTests[Id].bimonad[Int, Int, Int])
  checkAll("Bimonad[Id]", SerializableTests.serializable(Bimonad[Id]))

  checkAll("Id[Int]", CommutativeMonadTests[Id].commutativeMonad[Int, Int, Int])
  checkAll("CommutativeMonad[Id]", SerializableTests.serializable(CommutativeMonad[Id]))

  checkAll("Id[Int]", TraverseTests[Id].traverse[Int, Int, Int, Int, Option, Option])
  checkAll("Traverse[Id]", SerializableTests.serializable(Traverse[Id]))

  checkAll("Id[Int]", ReducibleTests[Id].reducible[Option, Int, Int])
  checkAll("Reducible[Id]", SerializableTests.serializable(Reducible[Id]))

  test("Id#apply") {
    forAll { (i: Int) =>
      val id = Id(i)
      assert(id === (i: Id[Int]))
      assert(id === i.pure[Id])
      assert(id === i)
    }
  }
}
