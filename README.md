APC
==

This module is used to inspect the request paramters in http request.

You can control these request which is not complete not to reach the controller layer.

You can uniformly process these broken request , you can send the FAIL response to the requester directly.

APC contains 3 moules:

* apc-web
    * to integrate with JavaEE
* apc-core
    * to get and cache api information and do inspection.
* apc-demo
    * JavaEE demo integrated with apc


How to use apc
---
4 steps:
* add filter in web.xml
* implement the net.moopa.apc.service.IApcService
* update the file - apc.config
* add annotation before the method in controller.