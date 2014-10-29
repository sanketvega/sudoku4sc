package sudoku4sc

import scala.io.Source

object Sudoku {

  type Block = Vector[Int]

  class Sudoku(val board: Vector[Vector[Int]], val blocks: Vector[Block]) {
    val ALL_INTS = Vector(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    def this(board: Vector[Vector[Int]]) {
      this(board, toBlocks(board))
    }

    // Constructor to read a Sudoku board from a CSV file
    def this(fileName: String) {
      this(Source.fromFile(fileName).getLines().toVector.map(line => line.split(',').map(x => x.toInt).toVector))
    }

    // To check if a Sudoku board is valid
    def isSudoku: Boolean = board.length == 9 && board.map(x => x.length == 9 && x.forall(ALL_INTS.contains(_))).reduce(_ & _)

    // To check if a Sudoku has been solved -> Simply check if there aren't any zeros
    def isSolved: Boolean = {
      !board.map(_.contains(0)).reduce(_ | _)
    }

    // To show Sudoku boards cleanly
    override def toString: String = {
      val str = for (row <- board) yield row.mkString(" ")
      str.mkString("\n", "\n", "\n")
    }
  }

  // To check if a Block( row, column or a (3 x 3) block is valid -> Simply check if all numbers appear once
  def isValidBlock(block: Block): Boolean = {
    val blockWithoutZero = block.filter(_ > 0)
    blockWithoutZero.distinct == blockWithoutZero && block.length == 9
  }


  // Convert a Sudoku board into blocks -> 9 rows, 9 columns, 9 (3x3) blocks
  def toBlocks(board: Vector[Vector[Int]]): Vector[Block] = {
    val rows = board
    val cols = for( i <- 0 until board.length) yield board.map(_(i))
    val blocks = for {
      i <- 0 until board.length by 3
      j <- 0 until board.length by 3
    } yield rows.slice(i,i+3).flatMap(_.slice(j,j+3))
    rows ++ cols ++ blocks
  }




}

