(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['anc_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data,depth1) {
  
  var buffer = "", stack1;
  buffer += "\n        <div data-form=\"";
  foundHelper = helpers.formToOpen;
  stack1 = foundHelper || depth0.formToOpen;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "formToOpen", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  foundHelper = helpers.caseId;
  stack1 = foundHelper || depth1.caseId;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "...caseId", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\" class=\"alert profile-alert todo\">\n            <i class=\"icon-warning-sign\"></i>\n            <strong>";
  foundHelper = helpers.message;
  stack1 = foundHelper || depth0.message;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "message", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</strong>\n        </div>\n        ";
  return buffer;}

function program3(depth0,data,depth1) {
  
  var buffer = "", stack1;
  buffer += "\n    <div data-form=\"";
  foundHelper = helpers.formToOpen;
  stack1 = foundHelper || depth0.formToOpen;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "formToOpen", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  foundHelper = helpers.caseId;
  stack1 = foundHelper || depth1.caseId;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "...caseId", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\" class=\"alert alert-info todo\">\n        <!-- <i class=\"icon-check\"></i> -->\n\n		<label class=\"checkbox\">\n			<input type=\"checkbox\"> \n	        <strong>";
  foundHelper = helpers.message;
  stack1 = foundHelper || depth0.message;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "message", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</strong>\n		</label>\n    </div>\n    ";
  return buffer;}

function program5(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                            <div class=\"text-center big-text high-risk\">\n                                <i class=\"icon-circle-arrow-up\" style=\"margin-top: 3px;\"></i> High Risk</span>\n                            </div>\n                            <div class=\"beneficiary-detail-footer risk-detail-text\">";
  foundHelper = helpers.pregnancyDetails;
  stack1 = foundHelper || depth0.pregnancyDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.riskDetail);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "pregnancyDetails.riskDetail", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                        ";
  return buffer;}

function program7(depth0,data) {
  
  
  return "\n                            <div class=\"text-center big-text normal-risk\">Normal Risk</div>\n                        ";}

function program9(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n\n                        <div class=\"container-section-component row-fluid\">\n\n\n							<div class = \"timeline\">\n								<dl>\n									<dt class = \"";
  stack1 = depth0.status;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "this.status", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\">\n									    <div class = \"span1\">\n											<i class = \"icon-eye-open\"></i>\n										</div>\n									</dt>\n									\n									<dd class = \"details-";
  stack1 = depth0.status;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "this.status", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\">\n										<div class = \"span7\">\n											<strong>";
  foundHelper = helpers.title;
  stack1 = foundHelper || depth0.title;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "title", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                            ";
  foundHelper = helpers.details;
  stack1 = foundHelper || depth0.details;
  stack2 = helpers.each;
  tmp1 = self.program(10, program10, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n											</div>\n                                         <div class=\"span4 pull-right text-right\">\n                                             ";
  foundHelper = helpers.date;
  stack1 = foundHelper || depth0.date;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "date", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\n                                         </div>\n                                    </dd>\n								</dl>\n                        </div>\n                    ";
  return buffer;}
function program10(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                                                ";
  stack1 = depth0;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "this", { hash: {} }); }
  buffer += escapeExpression(stack1) + "<br>\n                                            ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar affected-by-sidepanel\" href=\"#\">\n            <span class=\"navbar-brand-icon-holder affected-by-sidepanel\"><i class=\"icon-book icon-white navbar-brand-icon\"></i></span>\n            <span class=\"name\">ANC</span>\n        </a>\n\n        <ul class=\"nav pull-right affected-by-sidepanel\">\n            <li class=\"divider-vertical\"></li>\n            <li id=\"plusButton\" data-form=\"ANC_SERVICES\" data-caseid=";
  foundHelper = helpers.caseId;
  stack1 = foundHelper || depth0.caseId;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "caseId", { hash: {} }); }
  buffer += escapeExpression(stack1) + "><span class=\"navbar-text\" href=\"#\"><i class=\"icon-plus icon-white\"></i> UPDATE</span></li>\n        </ul>\n    </div>\n</div>\n\n<div id=\"content\" class=\"content affected-by-sidepanel\">\n    <div class=\"registry-container container-fluid\">\n        ";
  foundHelper = helpers.alerts;
  stack1 = foundHelper || depth0.alerts;
  stack2 = helpers.each;
  tmp1 = self.programWithDepth(program1, data, depth0);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <img src=\"../img/mother.png\"/>\n            </div>\n\n            <div id=\"ec-name\" class=\"span6\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
  foundHelper = helpers.womanName;
  stack1 = foundHelper || depth0.womanName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "womanName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.location;
  stack1 = foundHelper || depth0.location;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.villageName);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "location.villageName", { hash: {} }); }
  buffer += escapeExpression(stack1) + ", ";
  foundHelper = helpers.location;
  stack1 = foundHelper || depth0.location;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.subcenter);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "location.subcenter", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>TC: ";
  foundHelper = helpers.thaayiCardNumber;
  stack1 = foundHelper || depth0.thaayiCardNumber;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "thaayiCardNumber", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span2 pull-right\">\n                <a class=\"ec-information-button btn btn-inverse\" href=\"#\">\n                    <i class=\"icon-user icon-white\"></i>\n                    <i class=\"icon-align-justify icon-white\"></i>\n                </a>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div id=\"todos\" class=\"registry-container container-fluid\">\n    ";
  foundHelper = helpers.todos;
  stack1 = foundHelper || depth0.todos;
  stack2 = helpers.each;
  tmp1 = self.programWithDepth(program3, data, depth0);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n\n    <div class=\"divider\"></div>\n</div>\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        ANC Summary\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        <div class=\"container-fluid container-no-padding\">\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"beneficiary-detail-content\">\n                        ";
  foundHelper = helpers.pregnancyDetails;
  stack1 = foundHelper || depth0.pregnancyDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.isHighRisk);
  stack2 = helpers['if'];
  tmp1 = self.program(5, program5, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.program(7, program7, data);
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                </div>\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-content\">\n                        <div class=\"span6 text-center\">\n                            <span class=\"big-text\">";
  foundHelper = helpers.pregnancyDetails;
  stack1 = foundHelper || depth0.pregnancyDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.monthsPregnant);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "pregnancyDetails.monthsPregnant", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">Months Pregnant</span>\n                        </div>\n                        <div class=\"span6 text-center\">\n                            <span class=\"big-text\">";
  foundHelper = helpers.pregnancyDetails;
  stack1 = foundHelper || depth0.pregnancyDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.edd);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "pregnancyDetails.edd", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">EDD</span>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        Delivery Plan\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        <div class=\"container-fluid container-no-padding container-section\">\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"span1\"><!-- <i class=\"icon-check\"></i> --> \n					<label class=\"checkbox\">\n						<input type=\"checkbox\">\n					</label>\n				</div>\n				\n                <div class=\"span3 big-text\">Facility</div>\n                <div class=\"span8\">";
  foundHelper = helpers.facilityDetails;
  stack1 = foundHelper || depth0.facilityDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.facility);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "facilityDetails.facility", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n            </div>\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"span1\"><!-- <i class=\"icon-check\"></i> -->\n					<label class=\"checkbox\">\n						<input type=\"checkbox\">\n					</label>\n				</div>\n                <div class=\"span3 big-text\">Contact</div>\n                <div class=\"span8\">";
  foundHelper = helpers.facilityDetails;
  stack1 = foundHelper || depth0.facilityDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.contact);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "facilityDetails.contact", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n            </div>\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"span1\"><!-- <i class=\"icon-check\"></i> -->\n					<label class=\"checkbox\">\n						<input type=\"checkbox\">\n					</label>\n				</div>\n                <div class=\"span3 big-text\">ASHA</div>\n                <div class=\"span8\">";
  foundHelper = helpers.facilityDetails;
  stack1 = foundHelper || depth0.facilityDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.ashaName);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "facilityDetails.ashaName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        Timeline\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        <div class=\"container-fluid container-no-padding\">\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"beneficiary-detail-content\">\n                    <div class=\"container-section container-fluid\">\n                        ";
  foundHelper = helpers.timelineEvents;
  stack1 = foundHelper || depth0.timelineEvents;
  stack2 = helpers.each;
  tmp1 = self.program(9, program9, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n";
  return buffer;});
templates['anc_list'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n    <div class=\"row-fluid\">\n        <a data-caseId=";
  foundHelper = helpers.caseId;
  stack1 = foundHelper || depth0.caseId;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "caseId", { hash: {} }); }
  buffer += escapeExpression(stack1) + " class=\"client-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span9\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
  foundHelper = helpers.womanName;
  stack1 = foundHelper || depth0.womanName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "womanName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.villageName;
  stack1 = foundHelper || depth0.villageName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "villageName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>Thaayi #: ";
  foundHelper = helpers.thaayiCardNumber;
  stack1 = foundHelper || depth0.thaayiCardNumber;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "thaayiCardNumber", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span3 high-risk pull-text-completely-right\">\n                ";
  foundHelper = helpers.isHighRisk;
  stack1 = foundHelper || depth0.isHighRisk;
  stack2 = helpers['if'];
  tmp1 = self.program(2, program2, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </div>\n\n            <div class=\"divider divider-padding-bottom\"></div>\n        </a>\n    </div>\n";
  return buffer;}
function program2(depth0,data) {
  
  
  return " High Risk ";}

  foundHelper = helpers.anc;
  stack1 = foundHelper || depth0.anc;
  stack2 = helpers.each;
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n";
  return buffer;});
templates['ec_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  
  return "\n            <ul class=\"nav pull-right affected-by-sidepanel\">\n                <li class=\"divider-vertical\"></li>\n                <li><a class=\"ec-status high-priority\" href=\"#\">High Priority</a></li>\n            </ul>\n        ";}

function program3(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n            <div class=\"alert\">\n                <i class=\"icon-warning-sign\"></i>\n                <strong>Alert: ";
  foundHelper = helpers.message;
  stack1 = foundHelper || depth0.message;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "message", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</strong>\n            </div>\n        ";
  return buffer;}

function program5(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n                        ";
  foundHelper = helpers.isFemale;
  stack1 = foundHelper || depth0.isFemale;
  stack2 = helpers['if'];
  tmp1 = self.program(6, program6, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.program(8, program8, data);
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    ";
  return buffer;}
function program6(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                            <div class=\"ec-child girl\">\n                                <div style=\"text-align: center;\">\n                                    <span style=\"font-weight: bold\">";
  foundHelper = helpers.age;
  stack1 = foundHelper || depth0.age;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "age", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br>\n                                    Girl\n                                </div>\n                            </div>\n                        ";
  return buffer;}

function program8(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                            <div class=\"ec-child boy\">\n                                <div style=\"text-align: center;\">\n                                    <span style=\"font-weight: bold;\">";
  foundHelper = helpers.age;
  stack1 = foundHelper || depth0.age;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "age", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br>\n                                    Boy\n                                </div>\n                            </div>\n                        ";
  return buffer;}

function program10(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n                                    <div class=\"container-section-component row-fluid\">\n                                        <div class=\"span1\">\n                                            <i class=\"icon-filter\"></i>\n                                        </div>\n                                            <div class=\"span7\">\n                                                <strong>";
  foundHelper = helpers.title;
  stack1 = foundHelper || depth0.title;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "title", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                                ";
  foundHelper = helpers.details;
  stack1 = foundHelper || depth0.details;
  stack2 = helpers.each;
  tmp1 = self.program(11, program11, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                                            </div>\n                                        <div class=\"span4 pull-right text-right\">\n                                            ";
  foundHelper = helpers.date;
  stack1 = foundHelper || depth0.date;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "date", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\n                                        </div>\n                                    </div>\n                                ";
  return buffer;}
function program11(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                                                    ";
  stack1 = depth0;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "this", { hash: {} }); }
  buffer += escapeExpression(stack1) + "<br>\n                                                ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar affected-by-sidepanel\" href=\"#\">\n            <span class=\"navbar-brand-icon-holder affected-by-sidepanel\"><i class=\"icon-book icon-white navbar-brand-icon\"></i></span>\n            <span class=\"name\">EC</span>\n        </a>\n\n        ";
  foundHelper = helpers.isHighPriority;
  stack1 = foundHelper || depth0.isHighPriority;
  stack2 = helpers['if'];
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </div>\n</div>\n\n<div id=\"content\" class=\"content affected-by-sidepanel\">\n    <div class=\"registry-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <img src=\"../img/mother.png\"/>\n            </div>\n\n            <div id=\"ec-name\" class=\"span6\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
  foundHelper = helpers.wifeName;
  stack1 = foundHelper || depth0.wifeName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "wifeName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.village;
  stack1 = foundHelper || depth0.village;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "village", { hash: {} }); }
  buffer += escapeExpression(stack1) + ", ";
  foundHelper = helpers.subcenter;
  stack1 = foundHelper || depth0.subcenter;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "subcenter", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>EC: ";
  foundHelper = helpers.ecNumber;
  stack1 = foundHelper || depth0.ecNumber;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "ecNumber", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span2 pull-right\">\n                <a class=\"ec-information-button btn btn-inverse\" href=\"#\">\n                    <i class=\"icon-user icon-white\"></i>\n                    <i class=\"icon-align-justify icon-white\"></i>\n                </a>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        ";
  foundHelper = helpers.alerts;
  stack1 = foundHelper || depth0.alerts;
  stack2 = helpers.each;
  tmp1 = self.program(3, program3, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        <div class=\"alert alert-info\">\n            <i class=\"icon-check\"></i>\n            <strong>To do: Follow up on malaria test</strong>\n        </div>\n        <div class=\"divider\"></div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"big-text row-fluid detail-section-header\">\n            EC Summary\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-header\">Family Planning</div>\n                    <div class=\"beneficiary-detail-content\">\n                        <i class=\"icon-ok\"></i><span class=\"family-planning-current\">";
  foundHelper = helpers.currentMethod;
  stack1 = foundHelper || depth0.currentMethod;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "currentMethod", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br/>\n                        <a class=\"btn family-planning-button btn-large\" href=\"family_planning.html\">Family Planning Tool</a>\n                    </div>\n                    <div class=\"divider divider-padding-top\"></div>\n                </div>\n\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-header\">Children</div>\n                    <div class=\"beneficiary-detail-content\">\n                    ";
  foundHelper = helpers.children;
  stack1 = foundHelper || depth0.children;
  stack2 = helpers.each;
  tmp1 = self.program(5, program5, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </div>\n                    <div class=\"divider divider-padding-top\"></div>\n\n                    <div id=\"ec-timeline\" class=\"row-fluid beneficiary-detail-component\">\n                        <div class=\"beneficiary-detail-header\" data-toggle=\"collapse\" data-target=\"#timeline-toggled-content\">\n                            <i class=\"icon-calendar\"></i> View EC Timeline\n                            <a><i class=\"toggle-indicator icon-chevron-down pull-right\"></i></a>\n                        </div>\n                        <div class=\"beneficiary-detail-content collapse\" id=\"timeline-toggled-content\">\n                            <div class=\"divider divider-padding-both\"></div>\n                            <div class=\"container-section container-fluid\">\n                                ";
  foundHelper = helpers.timeline;
  stack1 = foundHelper || depth0.timeline;
  stack2 = helpers.each;
  tmp1 = self.program(10, program10, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                            </div>\n                        </div>\n                    </div>\n                    <script type=\"text/javascript\">\n                        toggleIconDuringCollapse(\"#ec-timeline\", \".toggle-indicator\", \"icon-chevron-down\", \"icon-chevron-up\");\n                    </script>\n\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <a class=\"ec-actions-button btn btn-large\" id=\"contactButton\">Contact</a>\n            </div>\n            <div class=\"span8\">\n                <a class=\"ec-actions-button btn btn-large\" id=\"newVisitButton\">New Visit</a>\n            </div>\n        </div>\n    </div>\n</div>\n";
  return buffer;});
templates['ec_list'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n    <div class=\"row-fluid\">\n        <a data-caseId=";
  foundHelper = helpers.caseId;
  stack1 = foundHelper || depth0.caseId;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "caseId", { hash: {} }); }
  buffer += escapeExpression(stack1) + " class=\"client-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span9\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
  foundHelper = helpers.wifeName;
  stack1 = foundHelper || depth0.wifeName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "wifeName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.village;
  stack1 = foundHelper || depth0.village;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "village", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>EC#: ";
  foundHelper = helpers.ecNumber;
  stack1 = foundHelper || depth0.ecNumber;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "ecNumber", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span3 high-risk pull-text-completely-right\">\n                    ";
  foundHelper = helpers.isHighRisk;
  stack1 = foundHelper || depth0.isHighRisk;
  stack2 = helpers['if'];
  tmp1 = self.program(2, program2, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </div>\n\n            <div class=\"divider divider-padding-bottom\"></div>\n        </a>\n    </div>\n";
  return buffer;}
function program2(depth0,data) {
  
  
  return "\n                        High Risk\n                    ";}

  foundHelper = helpers.ec;
  stack1 = foundHelper || depth0.ec;
  stack2 = helpers.each;
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n";
  return buffer;});
templates['home'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;


  buffer += "<div class=\"navbar navbar-fixed-top\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar affected-by-sidepanel\" href=\"#\">\n            <span class=\"name ellipsis\">";
  foundHelper = helpers.anmName;
  stack1 = foundHelper || depth0.anmName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "anmName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span>\n        </a>\n\n        <ul class=\"nav pull-right\">\n            <li class=\"divider-vertical list\"></li>\n            <li><i class=\"navbar-icon icon-cog icon-white\" href=\"#\"></i></li>\n        </ul>\n    </div>\n</div>\n\n<div class=\"content\">\n    <div class=\"menu-button-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"menu-button span6\" id=\"workplanButton\">\n                <img src=\"../img/icons/nav-workplan.png\" class=\"menu-icon\"><br>\n                Workplan\n            </div>\n            <div class=\"menu-button span6\" id=\"inboxButton\">\n                <img src=\"../img/icons/nav-inbox.png\" class=\"menu-icon\"><br>\n                Inbox\n            </div>\n        </div>\n        <div class=\"row-fluid menu-button-row-padding\">\n            <div class=\"menu-button span6\" id=\"myStatsButton\">\n                <img src=\"../img/icons/nav-activity.png\" class=\"menu-icon\"><br>\n                My Stats\n            </div>\n            <div class=\"menu-button span6\" id=\"reportsButton\">\n                <img src=\"../img/icons/nav-reporting.png\" class=\"menu-icon\"><br>\n                Reporting\n            </div>\n        </div>\n        <div class=\"menu-option row-fluid\" id=\"eligibleCoupleMenuOption\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-ec.png\">\n            </div>\n            <div class=\"span9\">Eligible Couple</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.eligibleCoupleCount;
  stack1 = foundHelper || depth0.eligibleCoupleCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "eligibleCoupleCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n        <div class=\"menu-option-divider\"></div>\n\n        <div class=\"menu-option row-fluid\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-anc.png\">\n            </div>\n            <div class=\"span9\">ANC</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.ancCount;
  stack1 = foundHelper || depth0.ancCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "ancCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n        <div class=\"menu-option-divider\"></div>\n\n        <div class=\"menu-option row-fluid\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-pnc.png\">\n            </div>\n            <div class=\"span9\">PNC</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.pncCount;
  stack1 = foundHelper || depth0.pncCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "pncCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n        <div class=\"menu-option-divider\"></div>\n\n        <div class=\"menu-option row-fluid\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-child.png\">\n            </div>\n            <div class=\"span9\">Child</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.childCount;
  stack1 = foundHelper || depth0.childCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "childCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n    </div>\n</div>\n";
  return buffer;});
templates['pnc_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                            <div class=\"text-center big-text high-risk\">\n                                <i class=\"icon-circle-arrow-up\" style=\"margin-top: 3px;\"></i> High Risk</span>\n                            </div>\n                            <div class=\"beneficiary-detail-footer risk-detail-text\">";
  foundHelper = helpers.pncDetails;
  stack1 = foundHelper || depth0.pncDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.riskDetail);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "pncDetails.riskDetail", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                        ";
  return buffer;}

function program3(depth0,data) {
  
  
  return "\n                            <div class=\"text-center big-text normal-risk\">Normal Risk</div>\n                        ";}

function program5(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n                    <div class=\"row-fluid\">\n                          <b> Delivery Complications </b>\n                          <ol>\n                              ";
  foundHelper = helpers.pncDetails;
  stack1 = foundHelper || depth0.pncDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.deliveryComplications);
  stack2 = helpers.each;
  tmp1 = self.program(6, program6, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                          </ol>\n                    </div>\n                    ";
  return buffer;}
function program6(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                                <li>\n                                ";
  stack1 = depth0;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "this", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\n                                </li>\n                              ";
  return buffer;}

function program8(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n                        <div class=\"container-section-component row-fluid\">\n                            <div class=\"span1\">\n                                <i class=\"icon-filter\"></i>\n                            </div>\n                            <div class=\"span7\">\n                                <strong>";
  foundHelper = helpers.title;
  stack1 = foundHelper || depth0.title;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "title", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                ";
  foundHelper = helpers.details;
  stack1 = foundHelper || depth0.details;
  stack2 = helpers.each;
  tmp1 = self.program(9, program9, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                            </div>\n                            <div class=\"span4 pull-right text-right\">\n                                ";
  foundHelper = helpers.date;
  stack1 = foundHelper || depth0.date;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "date", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\n                            </div>\n                        </div>\n                        ";
  return buffer;}
function program9(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                                ";
  stack1 = depth0;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "this", { hash: {} }); }
  buffer += escapeExpression(stack1) + "<br>\n                                ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar affected-by-sidepanel\" href=\"#\">\n            <span class=\"navbar-brand-icon-holder affected-by-sidepanel\"><i class=\"icon-book icon-white navbar-brand-icon\"></i></span>\n            <span class=\"name\">PNC</span>\n        </a>\n\n        <ul class=\"nav pull-right affected-by-sidepanel\">\n            <li class=\"divider-vertical\"></li>\n            <li id=\"plusButton\" data-form=\"ANC_SERVICES\" data-caseid=";
  foundHelper = helpers.caseId;
  stack1 = foundHelper || depth0.caseId;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "caseId", { hash: {} }); }
  buffer += escapeExpression(stack1) + "><span class=\"navbar-text\" href=\"#\"><i class=\"icon-plus icon-white\"></i> UPDATE</span></li>\n        </ul>\n    </div>\n</div>\n\n<div id=\"content\" class=\"content affected-by-sidepanel\">\n    <div class=\"registry-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <img src=\"../img/mother.png\"/>\n            </div>\n\n            <div id=\"ec-name\" class=\"span6\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
  foundHelper = helpers.womanName;
  stack1 = foundHelper || depth0.womanName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "womanName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.location;
  stack1 = foundHelper || depth0.location;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.villageName);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "location.villageName", { hash: {} }); }
  buffer += escapeExpression(stack1) + ", ";
  foundHelper = helpers.location;
  stack1 = foundHelper || depth0.location;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.subcenter);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "location.subcenter", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>Thayi: ";
  foundHelper = helpers.thaayiCardNumber;
  stack1 = foundHelper || depth0.thaayiCardNumber;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "thaayiCardNumber", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span2 pull-right\">\n                <a class=\"ec-information-button btn btn-inverse\" href=\"#\">\n                    <i class=\"icon-user icon-white\"></i>\n                    <i class=\"icon-align-justify icon-white\"></i>\n                </a>\n            </div>\n        </div>\n    </div>\n</div>\n\n\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        Summary\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        <div class=\"container-fluid container-no-padding\">\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"beneficiary-detail-content\">\n                        ";
  foundHelper = helpers.pncDetails;
  stack1 = foundHelper || depth0.pncDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.isHighRisk);
  stack2 = helpers['if'];
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.program(3, program3, data);
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                </div>\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-content\">\n                        <div class=\"span6 text-center\">\n                            <span class=\"big-text\">";
  foundHelper = helpers.pncDetails;
  stack1 = foundHelper || depth0.pncDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.daysPostpartum);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "pncDetails.daysPostpartum", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">Days Postpartum</span>\n                        </div>\n                        <div class=\"span6 text-center\">\n                            <span class=\"big-text\">";
  foundHelper = helpers.pncDetails;
  stack1 = foundHelper || depth0.pncDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.dateOfDelivery);
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "pncDetails.dateOfDelivery", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">Date of Delivery</span>\n                        </div>\n                    </div>\n                    <div class=\"divider\"></div>\n                    ";
  foundHelper = helpers.pncDetails;
  stack1 = foundHelper || depth0.pncDetails;
  stack1 = (stack1 === null || stack1 === undefined || stack1 === false ? stack1 : stack1.deliveryComplications);
  stack2 = helpers['if'];
  tmp1 = self.program(5, program5, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        PNC Plan\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        42 day checklist\n    </div>\n</div>\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        Timeline\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        <div class=\"container-fluid container-no-padding\">\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"beneficiary-detail-content\">\n                    <div class=\"container-section container-fluid\">\n                        ";
  foundHelper = helpers.timelineEvents;
  stack1 = foundHelper || depth0.timelineEvents;
  stack2 = helpers.each;
  tmp1 = self.program(8, program8, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n";
  return buffer;});
templates['pnc_list'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n    <div class=\"row-fluid\">\n        <a data-caseId=";
  foundHelper = helpers.caseId;
  stack1 = foundHelper || depth0.caseId;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "caseId", { hash: {} }); }
  buffer += escapeExpression(stack1) + " class=\"client-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span9\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
  foundHelper = helpers.womanName;
  stack1 = foundHelper || depth0.womanName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "womanName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.villageName;
  stack1 = foundHelper || depth0.villageName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "villageName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>Thaayi #: ";
  foundHelper = helpers.thaayiCardNumber;
  stack1 = foundHelper || depth0.thaayiCardNumber;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "thaayiCardNumber", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span3 high-risk pull-text-completely-right\">\n                ";
  foundHelper = helpers.isHighRisk;
  stack1 = foundHelper || depth0.isHighRisk;
  stack2 = helpers['if'];
  tmp1 = self.program(2, program2, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </div>\n\n            <div class=\"divider divider-padding-bottom\"></div>\n        </a>\n    </div>\n";
  return buffer;}
function program2(depth0,data) {
  
  
  return " High Risk ";}

  foundHelper = helpers.pnc;
  stack1 = foundHelper || depth0.pnc;
  stack2 = helpers.each;
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n";
  return buffer;});
templates['sidepanel'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;


  buffer += "<div id=\"sidepanel\" class=\"sidepanel affected-by-sidepanel\">\n    <div class=\"menu-background\">\n        <div class=\"navbar navbar-fixed-top navbar-sidepanel\">\n            <div class=\"navbar-inner nav-sidepanel\">\n                <span class=\"brand in-navbar in-sidepanel\" href=\"#\">";
  foundHelper = helpers.anmName;
  stack1 = foundHelper || depth0.anmName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "anmName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span>\n\n                <ul class=\"nav pull-right\">\n                    <li class=\"divider-vertical in-navbar-sidepanel\"></li>\n                    <li><i class=\"navbar-icon icon-cog icon-white\" href=\"#\"></i></li>\n                </ul>\n            </div>\n        </div>\n\n        <div class=\"content sidepanel-content\">\n            <div class=\"menu-button-container container-fluid\">\n                <div class=\"row-fluid\">\n                    <div class=\"menu-button span6\" id=\"workplanButton\">\n                        <img src=\"../img/icons/nav-workplan.png\" class=\"menu-icon\"><br>\n                        Workplan\n                    </div>\n                    <div class=\"menu-button span6\" id=\"inboxButton\">\n                        <img src=\"../img/icons/nav-inbox.png\" class=\"menu-icon\"><br>\n                        Inbox\n                    </div>\n                </div>\n                <div class=\"row-fluid menu-button-row-padding\">\n                    <div class=\"menu-button span6\" id=\"myStatsButton\">\n                        <img src=\"../img/icons/nav-activity.png\" class=\"menu-icon\"><br>\n                        My Stats\n                    </div>\n                    <div class=\"menu-button span6\" id=\"reportsButton\">\n                        <img src=\"../img/icons/nav-reporting.png\" class=\"menu-icon\"><br>\n                        Reporting\n                    </div>\n                </div>\n                <div class=\"menu-option row-fluid\" id=\"eligibleCoupleMenuOption\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-ec.png\">\n                    </div>\n                    <div class=\"span9\">Eligible Couple</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.eligibleCoupleCount;
  stack1 = foundHelper || depth0.eligibleCoupleCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "eligibleCoupleCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"menu-option-divider\"></div>\n\n                <div class=\"menu-option row-fluid\" id=\"ancMenuOption\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-anc.png\">\n                    </div>\n                    <div class=\"span9\">ANC</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.ancCount;
  stack1 = foundHelper || depth0.ancCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "ancCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"menu-option-divider\"></div>\n\n                <div class=\"menu-option row-fluid\" id=\"pncMenuOption\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-pnc.png\">\n                    </div>\n                    <div class=\"span9\">PNC</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.pncCount;
  stack1 = foundHelper || depth0.pncCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "pncCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"menu-option-divider\"></div>\n\n                <div class=\"menu-option row-fluid\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-child.png\">\n                    </div>\n                    <div class=\"span9\">Child</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.childCount;
  stack1 = foundHelper || depth0.childCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "childCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div id=\"mainpanel-overlay\" class=\"affected-by-sidepanel\">\n</div>\n";
  return buffer;});
templates['workplan'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression, blockHelperMissing=helpers.blockHelperMissing;

function program1(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n        <tr class=\"workplanElement\" data-village=";
  foundHelper = helpers.name;
  stack1 = foundHelper || depth0.name;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "name", { hash: {} }); }
  buffer += escapeExpression(stack1) + ">\n            <td><span class=\"summary\">";
  foundHelper = helpers.name;
  stack1 = foundHelper || depth0.name;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "name", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br/><span class=\"village-breakdown\">";
  foundHelper = helpers.reminderCount;
  stack1 = foundHelper || depth0.reminderCount;
  foundHelper = helpers.ifNotZero;
  stack2 = foundHelper || depth0.ifNotZero;
  tmp1 = self.program(2, program2, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  if(foundHelper && typeof stack2 === functionType) { stack1 = stack2.call(depth0, stack1, tmp1); }
  else { stack1 = blockHelperMissing.call(depth0, stack2, stack1, tmp1); }
  if(stack1 || stack1 === 0) { buffer += stack1; }
  foundHelper = helpers.alertCount;
  stack1 = foundHelper || depth0.alertCount;
  foundHelper = helpers.ifNotZero;
  stack2 = foundHelper || depth0.ifNotZero;
  tmp1 = self.program(4, program4, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  if(foundHelper && typeof stack2 === functionType) { stack1 = stack2.call(depth0, stack1, tmp1); }
  else { stack1 = blockHelperMissing.call(depth0, stack2, stack1, tmp1); }
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "</span>\n            </td>\n        </tr>\n    ";
  return buffer;}
function program2(depth0,data) {
  
  var buffer = "", stack1;
  foundHelper = helpers.reminderCount;
  stack1 = foundHelper || depth0.reminderCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "reminderCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + " Reminders, ";
  return buffer;}

function program4(depth0,data) {
  
  var buffer = "", stack1;
  foundHelper = helpers.alertCount;
  stack1 = foundHelper || depth0.alertCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "alertCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + " Alerts";
  return buffer;}

  buffer += "<table class=\"table table-bordered\">\n    <tbody>\n    <tr>\n        <td><i class=\"summary-icon icon-user\"></i><span class=\"summary\">";
  foundHelper = helpers.totalAlertCount;
  stack1 = foundHelper || depth0.totalAlertCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "totalAlertCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + " New Alerts</span></td>\n    </tr>\n    <tr>\n        <td><i class=\"summary-icon icon-user\"></i><span class=\"summary\">";
  foundHelper = helpers.totalReminderCount;
  stack1 = foundHelper || depth0.totalReminderCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "totalReminderCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + " New Reminder</span></td>\n    </tr>\n    </tbody>\n</table>\n\n<h3 class=\"village-header\">Villages</h3>\n\n<table class=\"table table-bordered\">\n    <tbody id=\"workplan-list\">\n    ";
  foundHelper = helpers.villages;
  stack1 = foundHelper || depth0.villages;
  stack2 = helpers.each;
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </tbody>\n</table>\n";
  return buffer;});
templates['workplan_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n        <div class=\"alert high-priority\">\n            <div class=\"row-fluid\">\n                <div class=\"span1\"><i class=\"icon-warning-sign\"></i></div>\n                <div class=\"span11\">\n                    <div class=\"beneficiaryName\">";
  foundHelper = helpers.beneficiaryName;
  stack1 = foundHelper || depth0.beneficiaryName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "beneficiaryName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                    <div class=\"dueDate\">";
  foundHelper = helpers.dueDate;
  stack1 = foundHelper || depth0.dueDate;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "dueDate", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                    <div class=\"detail\">";
  foundHelper = helpers.description;
  stack1 = foundHelper || depth0.description;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "description", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n            </div>\n        </div>\n    ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar ellipsis affected-by-sidepanel\" href=\"#\">\n            <span class=\"navbar-brand-icon-holder affected-by-sidepanel\"><i class=\"icon-book icon-white navbar-brand-icon\"></i></span>\n            <span class=\"name\">Workplan: ";
  foundHelper = helpers.village;
  stack1 = foundHelper || depth0.village;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "village", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span>\n        </a>\n    </div>\n</div>\n\n<div id=\"content\" class=\"content  affected-by-sidepanel\">\n    <h3 class=\"priority-header\">Soon</h3>\n    ";
  foundHelper = helpers.alerts;
  stack1 = foundHelper || depth0.alerts;
  stack2 = helpers.each;
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    <div class=\"alert low-priority\">\n            <div class=\"row-fluid\">\n                <div class=\"span1\"><i class=\"icon-warning-sign\"></i></div>\n                <div class=\"span11\">\n                    <div class=\"beneficiaryName\">Akarishma</div>\n                    <div class=\"villageName\">Bherya</div>\n                    <div class=\"detail\">Lorem ipsum dolor sit amet</div>\n                </div>\n            </div>\n    </div>\n</div>\n";
  return buffer;});
})();