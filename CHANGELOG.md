# Changelog
All notable changes to the 2.1-develop branch will be documented in this file.

## [Unreleased]

## [2.10] - 2018-10-16
### Added
- Use startup property hudson.plugins.ec2.t2unlimited=true to enable T2 Unlimited
### Changed
- Requires Jenkins AWS Java SDK plugin version 1.11.248 or newer

## [2.00] - 2018-02-28
### Changed
- Port JENKINS-47593 for plugin to be compatible with Jenkins 2.89

## [1.47] - 2017-11-27
### Changed
- Disable AWS slave count cache by default. Use hudson.plugins.ec2.EC2Cloud.cachettl to specify cache in milliseconds

## [1.46] - 2017-11-13
### Added
- Timeout slave after max connect time has been reached (improvement for T2 AWS tiers). Use hudson.plugins.ec2.EC2RetentionStrategy.maxConnectTimeSeconds
### Changed
- Skip idle timeout check if slave connect time is less than 5 minutes (improvement for Jenkins master startup)

## [1.45] - 2017-11-13
### Changed
- Disconnect Jenkins from the slave before shutting down the slave
- Only shutdown online Jenkins slaves (skip shutting down stopped slaves)

## [1.44] - 2017-10-31
### Added
- Cache AWS slave count 10 minutes when last value from AWS is 0
- Null pointer check on bootstrap connections backported from 1.37
- If we are unable to SSH into a slave because it is stopped, start the slave
### Changed
- Don't count stopped slaves as an existing node available for work
- Don't count VMs in AWS if configured EC2 Plugin cloud instance cap is 10000+
- Don't try to create a slave when we have 0 capacity
- Since getNewOrExistingAvailableSlave returns the same slave, only start it once
- When using billable hour retention, only shutdown a slave if it's at the end of the billable hour and has been idle 30 seconds
- Only run the slave monitor thread when there are no busy executors

## [1.40] - 2017-9-6
### Changed
- Obtain SSH key once during slave node startup cycle
- Use cached AWS state for better performance (describeInstance vs updateInstanceDescription)
### Removed
- Removed unused bootstrapConn variable reference
