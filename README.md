# OakTest
A selenium framework that requires no coding knowledge

This is the execution part of the overall framework - the part that runs the tests/drives browsers/sends HTTP requests.

OakTest accepts JSON or XML from a Rabbit queue and executes them against the given URL.

Results are passed back to another Rabbit queue as JSON. The executor is database-independant.

We're planning to store screenshots (and possibly videos) in S3 buckets.
