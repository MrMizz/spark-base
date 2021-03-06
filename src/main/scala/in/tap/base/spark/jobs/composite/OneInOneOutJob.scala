package in.tap.base.spark.jobs.composite

import in.tap.base.spark.jobs.in.dataset.OneInJob
import in.tap.base.spark.jobs.out.dataset.OneOutJob
import in.tap.base.spark.main.InArgs.OneInArgs
import in.tap.base.spark.main.OutArgs.OneOutArgs
import org.apache.spark.sql.{Dataset, SparkSession}

abstract class OneInOneOutJob[A <: Product, B <: Product](inArgs: OneInArgs, outArgs: OneOutArgs)(
  implicit spark: SparkSession
) extends CompositeJob(inArgs, outArgs)
    with OneInJob[A]
    with OneOutJob[B] {

  def transform(input: Dataset[A]): Dataset[B]

  override final def execute(): Unit = {
    write(transform(read))
  }

}
