# OakTest
A selenium framework that requires no coding knowledge

Strictly speaking, this is the test executor part of the planned overall framework.

OakTest accepts JSON or XML from a defined Rabbit queue and executes them against the given URL.

We intend to pass results back to another Rabbit queue as JSON so that the executor is as far as possible DB-independant. The one caveat to that will be screenshots, as we intend to store them in S3 buckets defined by the message generator.
