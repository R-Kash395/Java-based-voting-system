==> Secure Online Voting System REST API

This is a thread-safe backend application built using Java and Spring Boot designed to manage secure ballot casting and live election tallying. 
The core focus of this project is resolving classic distributed system challenges, specifically ensuring strict security clearance, enforcing idempotency to block duplicate votes, 
and maintaining absolute data consistency under concurrent traffic.

==> Core Architecture and Features

- Role-Based Security: Implements Spring Security to strictly partition user actions. Regular users are restricted to ballot submission endpoints under the VOTER role,
  while administrative metrics are shielded behind the ADMIN role.
- Concurrency Control: To prevent race conditions during high-volume, simultaneous voting windows, the application completely avoids blocking synchronization primitives.
  Instead, it relies on high-performance concurrent data structures, utilizing ConcurrentHashMap and AtomicInteger to safely manage state across multiple application threads.
- Idempotency and Deduplication: Includes an in-memory tracking mechanism that cross-references incoming requests against a historic cache of unique voter IDs,
  ensuring each registered voter can only alter the election state exactly once.

==>Technologies Used
- Core Language: Java 17
- Framework Ecosystem: Spring Boot 3.x
- Security Engine: Spring Security utilizing Basic Authentication
- Architectural Pattern: Controller-Service-DTO separation for clean decoupling of HTTP routing, business workflows, and data transport layers

==> API Endpoints

1 Cast a Ballot
- URL: /api/vote/cast
- HTTP Method: POST
- Authorization: Requires valid credentials mapped to the VOTER role.
- Expected Request Body: Accepts a JSON payload containing the voterId string and the selected candidateName.

2 View Live Results
- URL: /api/admin/results
- HTTP Method: GET
- Authorization: Requires valid credentials mapped to the ADMIN role.
- Expected Response: Returns a structured JSON map detailing the current vote totals per candidate along with aggregate election participation metrics.
