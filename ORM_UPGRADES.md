Occasionally the Object Relational Mapper system will need updates to include new data or to restructure old data.

To achieve these upgrades, Oatmeal keeps an internal copy of a database "Version".
This version is different than the version used for different versions of Oatmeal. 
This version is a simply incrementing integer, and will increase only when the ORM changes.

This allows for upgrade scripts to be written efficiently.
An ORM version number is finalized when it is pushed to master.
In the rare case where this conflicts with ongoing PRs, those PRs should increment their version number accordingly.
