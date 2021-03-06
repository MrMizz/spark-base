package in.tap.base.spark.jobs.composite

import in.tap.base.spark.jobs.in.dataset.TwoInJob
import in.tap.base.spark.jobs.out.dataset.TwoOutJob
import in.tap.base.spark.main.InArgs.TwoInArgs
import in.tap.base.spark.main.OutArgs.TwoOutArgs
import org.apache.spark.sql.{Dataset, SparkSession}

abstract class TwoInTwoOutJob[A <: Product, B <: Product, C <: Product, D <: Product](
  inArgs: TwoInArgs,
  outArgs: TwoOutArgs
)(
  implicit spark: SparkSession
) extends CompositeJob(inArgs, outArgs)
    with TwoInJob[A, B]
    with TwoOutJob[C, D] {

  def transform(input: (Dataset[A], Dataset[B])): (Dataset[C], Dataset[D])

  override final def execute(): Unit = {
    val (ds1: Dataset[C], ds2: Dataset[D]) = transform(read)
    write(ds1, ds2)
  }

}
