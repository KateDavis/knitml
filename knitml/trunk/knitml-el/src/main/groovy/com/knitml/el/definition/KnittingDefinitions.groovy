package com.knitml.el.definitionimport static com.knitml.core.common.DataConversion.serializeIntArrayimport groovy.xml.MarkupBuilderimport com.knitml.core.model.directions.block.RepeatInstructionimport com.knitml.core.model.directions.inline.Repeatimport com.knitml.core.common.Wiseimport com.knitml.core.common.Sideimport com.knitml.core.common.LoopToWorkimport com.knitml.core.common.EnumUtilsimport com.knitml.core.common.NeedleStyleimport com.knitml.core.common.CrossTypeimport com.knitml.core.common.YarnPositionimport org.apache.commons.lang.StringUtilsimport javax.measure.Measureimport javax.measure.unit.Unitimport com.knitml.core.units.*   class KnittingDefinitions {		private def builder	private boolean currentlyInRow = false		private static final List NO_MANIPULATION_ELEMENTS =		['directions','pattern','instructionDefinitions',		 'copyrightInfo','firstName','lastName',		 'designateEndOfRow','joinInRound','placeMarker',		 'removeMarker','declareRoundKnitting']	private static final List UNIT_ELEMENTS =		['stitchGauge','rowGauge','ballWeight','ballLength',		 'totalLength','totalWeight','thickness',		 'length','size']		KnittingDefinitions(builder) {		this.builder = builder	}		def methodMissing(String name, args) {		if (NO_MANIPULATION_ELEMENTS.contains(name)) {			builder.invokeMethod (fromCamelCase(name), args)		} else if (UNIT_ELEMENTS.contains(name)) {			processUnit(fromCamelCase(name),args)		} else {			builder.invokeMethod(name, args)		}	}	private void processUnit(String methodName, Object[] args) {		def number		Unit unit 		args.each {			if (it instanceof Number || it instanceof String) {				number = it			} else if (it instanceof Unit) {				unit = it			}		}		builder.invokeMethod(methodName,[[unit:unit],number])	}		protected String fromCamelCase(String name) {		StringBuffer result = new StringBuffer()		name.toCharArray().each {			if (Character.isUpperCase(it)) {				result << '-' << Character.toLowerCase(it)			} else {				result << it			}		}		return result	}		def inches = Units.INCH	def inch = inches 	def ins = inches 	def centimeters = Units.CENTIMETER	def cm = centimeters	def millimeters = Units.MILLIMETER	def mm = millimeters	def meters = Units.METER	def m = meters	def yd = Units.YARD	def yards = Units.YARD	def grams = Units.GRAM	def g = grams	def ounces = Units.OUNCE	def oz = ounces	def stitchesPerInch = Units.STITCHES_PER_INCH	def stitchesPerCentimeter = Units.STITCHES_PER_CENTIMETER	def rowsPerInch = Units.ROWS_PER_INCH	def rowsPerCentimeter = Units.ROWS_PER_CENTIMETER	def wrapsPerInch = Units.WRAPS_PER_INCH	def wrapsPerCentimeter = Units.WRAPS_PER_CENTIMETER	def US = Units.NEEDLE_SIZE_US	def knitwise = Wise.KNITWISE	def purlwise = Wise.PURLWISE		def rightSide = Side.RIGHT	def wrongSide = Side.WRONG		def inFrontOf = CrossType.FRONT	def behind = CrossType.BACK	// 'fer' is slang for 'for' since 'for' is a keyword in Groovy	def fer = KnittingKeyword.FER	def until = KnittingKeyword.UNTIL	def next = KnittingKeyword.NEXT	def with = KnittingKeyword.WITH	def to = KnittingKeyword.TO	def from = KnittingKeyword.FROM		def st = KnittingKeyword.ST	def sts = KnittingKeyword.STS	def stitch = st	def stitches = st	def withKey = KnittingKeyword.WITH_KEY	def withLabel = KnittingKeyword.WITH_LABEL	def holder = KnittingKeyword.HOLDER	def stateStitchesRemaining = ['inform-unworked-stitches':true]	def resetRowCount = KnittingKeyword.RESET_ROW_COUNT	def silently = KnittingKeyword.SILENTLY	def allStitches = KnittingKeyword.ALL_STITCHES	def all = allStitches	def informSide = KnittingKeyword.INFORM_SIDE	def doNotAssignNumber = KnittingKeyword.DO_NOT_ASSIGN_NUMBER 	//deprecated	def countAsRow = KnittingKeyword.COUNT_AS_ROW	def round = KnittingKeyword.ROUND	def flat = KnittingKeyword.FLAT	def left = KnittingKeyword.LEFT	def inRow = KnittingKeyword.IN_ROW	def stitchesLeftOnNeedles = inRow 	def onNeedles = inRow	def onNeedle = KnittingKeyword.ON_NEEDLE	def remain = KnittingKeyword.REMAIN	def rem = remain	def remaining = remain	def remains = remain	def unworked = KnittingKeyword.UNWORKED	def before = KnittingKeyword.BEFORE	def reverse = KnittingKeyword.REVERSE	def inReverse = reverse	def twice = 2	def even = KnittingKeyword.EVEN	def evenly = even		def measures = RepeatInstruction.Until.UNTIL_MEASURES	def desiredLength = RepeatInstruction.Until.UNTIL_DESIRED_LENGTH	def additionalTimes = RepeatInstruction.Until.ADDITIONAL_TIMES	def additionalTime = additionalTimes	def times = Repeat.Until.TIMES	def beforeGap = Repeat.Until.BEFORE_GAP	def gap = beforeGap	def end = Repeat.Until.END	def beforeEnd = Repeat.Until.BEFORE_END	def beforeMarker = Repeat.Until.BEFORE_MARKER	def marker = Repeat.Until.MARKER		def circular = NeedleStyle.CIRCULAR	def circ = circular	def straight = NeedleStyle.STRAIGHT	def dpn = NeedleStyle.DPN		def tbl = LoopToWork.TRAILING	def wyib = YarnPosition.BACK	def wyif = YarnPosition.FRONT		// function names are below		def gauge(Map args, Closure cl) { builder.gauge(args,cl) }	def gauge(String type, Closure cl) { gauge([type:type],cl) }	def instructionRef(String idref) { builder.'instruction-ref'(ref:idref) }	def inlineInstructionRef(String idref) { builder.'inline-instruction-ref'(ref:idref) }	def ref(String idref) { inlineInstructionRef(idref) }	def applyNextRow(String idref) { builder.'apply-next-row'('instruction-ref':idref) }	def forEachRowInInstruction(String idref, Closure cl) { builder.'for-each-row-in-instruction'(ref:idref,cl) }	def yarnType(Object[] args)    { processFunctionWithStringArg('yarn-type','id',args) }	def color(Object[] args)       { processFunctionWithStringArg('color','name',args) }	def usingNeedle (Object[] args){ processFunctionWithStringArg('using-needle','ref',args)}	private void processFunctionWithStringArg(String methodName, String stringArgName, Object[] args) {		def attrsToUse = [:]		def cl = {}		args.each {			if (it instanceof String) {				attrsToUse.put(stringArgName,it)			} else if (it instanceof Map) {				attrsToUse.putAll(it)			} else if (it instanceof Closure) {				cl = it			}		}		builder.invokeMethod(methodName,[attrsToUse,cl])	}		def needleType(Object[] args) {		def attrsToUse = [:]		def cl = {}		args.each {			if (it instanceof String) {				attrsToUse.put('id',it)			} else if (it instanceof NeedleStyle) {				attrsToUse.put('type',it.toString().toLowerCase())			} else if (it instanceof Map) {				attrsToUse.putAll(it)			} else if (it instanceof Closure) {				cl = it			}		}		builder.'needle-type'(attrsToUse, cl)	}		def declareFlatKnitting(Object[] args) {		def attrsToUse = [:]		def cl = {}		args.each {			if (it instanceof Side) {				attrsToUse.put('next-row-side',EnumUtils.fromEnum(it))			} else if (it instanceof Map) {				attrsToUse.putAll(it)			} else if (it instanceof Closure) {				cl = it			}		}		builder.'declare-flat-knitting'(attrsToUse, cl)	}		def mergedInstruction(Object[] args){ processIdAndKeyElement('merged-instruction',args) }	def inlineInstruction(Object[] args){ processIdAndKeyElement('inline-instruction',args) }	def instructionGroup(Object[] args) { processIdAndKeyElement('instruction-group',args) }		def yarn(Object[] args)             { processIdAndKeyElement('yarn',args) }	def needle(Object[] args)           { processIdAndKeyElement('needle',args) }	def stitchHolder(Object[] args)     { processIdAndKeyElement('stitch-holder',args) }		def instruction(Object[] args) {		def attrMap		List newArgs = args		boolean roundAttribute = false		boolean flatAttribute = false		args.each {			if (it instanceof Map) {				attrMap = it 			} else if (it instanceof KnittingKeyword && it == round) {				roundAttribute = true			} else if (it instanceof KnittingKeyword && it == flat) {				flatAttribute = true			}		}		if (roundAttribute || flatAttribute) {			if (attrMap == null) {				attrMap = [:]				newArgs << attrMap			}			attrMap.put('shape',(roundAttribute ? 'round' : 'flat'))		}		processIdAndKeyElement('instruction',newArgs.toArray())	}		/**	 * For processing yarn, instruction-group, and needle elements	 */	private void processIdAndKeyElement(String methodName, Object[] args) {		def id		def attrsToUse = [:]		def cl = {}		for (int i = 0; i < args.length; i++) {			def it = args[i]			if (it instanceof String && id == null) {				id = it				attrsToUse.put('id',id)			} else if (it instanceof KnittingKeyword && it == withKey) {				String messageKey				// if there is a next parameter and it's a string, that's the message-key to use 				if (i+1 < args.length && args[i+1] instanceof String) {					messageKey = args[i+1]					i++				} else {					messageKey = methodName + '.' + id				}				attrsToUse.put('message-key',messageKey)			} else if (it instanceof KnittingKeyword && it == withLabel) {				String label				if (i+1 < args.length && args[i+1] instanceof String) {					attrsToUse.put('label',args[i+1])					i++				} else {					throw new RuntimeException('Using a withLabel keyword requires the next parameter to be a string')				}			} else if (it instanceof KnittingKeyword && it == KnittingKeyword.RESET_ROW_COUNT) {				attrsToUse.put('reset-row-count',true)			} else if (it instanceof Map) {				attrsToUse.putAll(it)			} else if (it instanceof Closure) {				cl = it			}		}		builder.invokeMethod(methodName,[attrsToUse,cl])	}			def pm() { builder.'place-marker'() }	def pattern(String language, Closure cl) {		builder.'pattern'('xml:lang':language,cl)	}		def generalInformation(String language, Closure cl) {		builder.'general-information'('xml:lang':language,cl)	}	def generalInformation(Map attrs, Closure cl) {		builder.'general-information'(attrs,cl)	}	def generalInformation(Closure cl) {		builder.'general-information' cl	}		def informationalMessage(Object[] args) {		def attrs = [:]		for (int i = 0; i < args.length; i++) {			def it = args[i]			if (it instanceof KnittingKeyword && it == withKey) {				String messageKey				// if there is a next parameter and it's a string, that's the message-key to use 				if (i+1 < args.length && args[i+1] instanceof String) {					messageKey = args[i+1]					i++				} else {					throw new RuntimeException('Using a withKey keyword requires the next parameter to be a string')				}				attrs.put('message-key',messageKey)			} else if (it instanceof KnittingKeyword && it == withLabel) {				String label				if (i+1 < args.length && args[i+1] instanceof String) {					attrs.put('label',args[i+1])					i++				} else {					throw new RuntimeException('Using a withLabel keyword requires the next parameter to be a string')				}			} else if (it instanceof String) {				attrs.put('message-key',it)			}		}		if (currentlyInRow) {			builder.'followup-information' { message attrs }		} else {			builder.information { message attrs }		}	}		def section(Object[] args) {		def attrs = [:]		def cl		args.each {			if (it instanceof Closure) {				cl = it			} else if (it == resetRowCount) {				attrs.put('reset-row-count',true)			}		}		builder.section(attrs,cl)	}		def shortRow(Object[] args) {		def newArgs = addMapAttributeToArgs('short',true,args)		row(newArgs)	}		def longRow(Object[] args) {		def newArgs = addMapAttributeToArgs('long',true,args)		row(newArgs)	}	def longRound(Object[] args) {		def newArgs = addMapAttributeToArgs('long',true,args)		round(newArgs)	}		def nextRow(Object[] args) {		def newArgs = asList (args)		newArgs << doNotAssignNumber 		row (newArgs.toArray())	}		def nextRound(Object[] args) {		def newArgs = asList (args)		newArgs << doNotAssignNumber 		round (newArgs.toArray())	}		def round(Object[] args) {		def newArgs = addMapAttributeToArgs('type','round',args)		row (newArgs)	}		private List asList(Object[] args) {		def argList = []		args.each {			argList << it		}		return argList	}		private def addMapAttributeToArgs(name, value, args) {		def attrs = null		args.each {			if (it instanceof Map) {				attrs = it			}		}		if (attrs == null) {			attrs = [:]			def argList = []			args.each {				argList << it			}			argList << attrs			args = argList.toArray()		}		attrs.put(name, value)		return args	}		def row(Object[] args) {		def attrs = [:]		def cl		args.each {			if (it instanceof String) {				attrs.put('yarn-ref',it)			} else if (it instanceof Number) { 				attrs.put('number',it)			} else if (it instanceof List) {				int[] rowNumbers = it				attrs.put('number',serializeIntArray(rowNumbers))			} else if (it instanceof Map) {				attrs.putAll(it)			} else if (it instanceof Closure) {				cl = it			} else if (it instanceof KnittingKeyword && it == KnittingKeyword.INFORM_SIDE) {				attrs.put('inform-side',true)			} else if (it instanceof KnittingKeyword && it == KnittingKeyword.DO_NOT_ASSIGN_NUMBER) {				attrs.put('assign-row-number',false)			} else if (it instanceof Side) {				attrs.put('side',EnumUtils.fromEnum(it))			}		}		currentlyInRow = true		builder.row(attrs, cl)		currentlyInRow = false	}	def co (Object[] args) {		castOn(args)	}	def cO (Object[] args) {		// For matching 'CO'		castOn(args)	}		def castOn(Object[] args) {		Integer number		def attrs = [:]		args.each {			if (it instanceof Number) {				number = Integer.valueOf(it)			} else if (it instanceof String) {				attrs.put 'style', it			} else if (it instanceof KnittingKeyword) {				if (it == countAsRow) {					attrs.put 'count-as-row', true				}			} else if (it instanceof Map) {				attrs.putAll it			}		}		if (number != null) {			builder.'cast-on' attrs, number		} else {			builder.'cast-on' attrs		}	}		def k2tog(Object[] args) {		String type = 'k2tog'		Number number		args.each {			if (it instanceof Number) {				number = it			} else if (it instanceof LoopToWork && it == tbl) {				type = 'k2tog-tbl'			}		}		if (number != null) {			builder.decrease([type:type], number)		} else {			builder.decrease([type:type])		}	}	def p2tog(Object[] args) {		String type = 'p2tog'		Number number		args.each {			if (it instanceof Number) {				number = it			} else if (it instanceof LoopToWork && it == tbl) {				type = 'p2tog-tbl'			}		}		if (number != null) {			builder.decrease([type:type], number)		} else {			builder.decrease([type:type])		}	}		// decreases		def ssk() { builder.decrease(type:'ssk') }	// double decreases	def cdd(Object[] args) { dblDecrease('cdd',args) }	def s2kp(Object[] args) { cdd(args) }	def sk2p(Object[] args) { dblDecrease('sk2p',args) }	def k3tog(Object[] args) { dblDecrease('k3tog',args) }	def p3tog(Object[] args) { dblDecrease('p3tog',args) }	def sssk(Object[] args)  { dblDecrease('sssk',args)  }		private void dblDecrease(type, Object[] args) {		Number number		args.each {			if (it instanceof Number) {				number = it			}		}		builder.'double-decrease'([type:type], number)	}		// increases	def m (int numberToIncrease) {		for (int i=0; i < numberToIncrease; i++) {			builder.'increase'()		}	}	def yo(Object[] args) { increaseInternal('yo',args) }	def m1p(Object[] args) { increaseInternal('m1p',args) }	def m1a(Object[] args) { increaseInternal('m1a',args) }	def kfb(Object[] args) { increaseInternal('kfb',args) }		private void increaseInternal(type, Object[] args) {		Number number		args.each {			if (it instanceof Number) {				number = it			}		}		builder.'increase'([type:type], number)	}		def k (Object[] args) {		knitOrPurl('knit',args)	}	def p (Object[] args) {		knitOrPurl('purl',args)	}		private void knitOrPurl (methodName,Object[] args) {		Number number		Map attrs = [:]		Repeat.Until until		boolean beforeFlag = false		boolean holderFlag = false		String holderId = null		args.each {			if (it instanceof Number) {				number = it			} else if (it instanceof String) {				if (holderFlag) {					holderId = it				} else {					attrs.put('yarn-ref',it)				}			} else if (it instanceof LoopToWork && it == LoopToWork.TRAILING) {				attrs.put('loop-to-work',EnumUtils.fromEnum(it))			} else if (it instanceof Map) {				attrs = it			} else if (it instanceof Repeat.Until) {				until = it			} else if (it instanceof KnittingKeyword) {				if (it == before || it == from) {					beforeFlag = true				} else if (it == holder) {					holderFlag = true				}			}		}		if (until != null) {			attrs.clear()			if (beforeFlag) {				until = addBeforeDescriptor(until)			}			attrs.put('until',EnumUtils.fromEnum(until))			if (number != null) {				attrs.put('value',number)			}			builder.repeat (attrs) { builder.invokeMethod(methodName) }		} else if (holderId != null) {		    builder.'from-stitch-holder' (ref:holderId) { builder.invokeMethod(methodName, [attrs, number]) }	    } else {			builder.invokeMethod(methodName, [attrs, number])		}	}		def workEven (Object[] args) {		Number number		def newArgs = []		Map attrs = [:]		// filter out any "noisy" args before calling knitOrPurl		args.each {			if (it instanceof Number) {				newArgs << it			} else if (it instanceof String) {				newArgs << it			} else if (it instanceof Repeat.Until) {				newArgs << it			} else if (it instanceof KnittingKeyword && it == before) {				newArgs << it			}		}		knitOrPurl('work-even',newArgs.toArray())	}		def work (Object[] args) {//		args.each {//			if (it instanceof KnittingKeyword && it == even) {//				workEven args//			}//		}		workEven(args)	}		def cross(int first, CrossType crossType, int next) {		def attrs = [first:first, next:next]		attrs.put('type', EnumUtils.fromEnum(crossType))		builder.'cross-stitches' attrs	}		def crossStitches(int first, CrossType crossType, int next) {		cross(first, crossType, next)	}	def graftTogether(Object[] needles) {		builder.'graft-together' {			needles.each { id ->			builder.needle ref:id }		}	}		def messageSource(ms) { messageSources(ms) }		def messageSources(Object[] messageSources) {		builder.'message-sources' {			messageSources.each { location ->			builder.'message-source' location }		}	}		def useNeedles(Object[] needles) {		builder.'use-needles' {			needles.each { id ->			builder.needle ref:id }		}	}	def useNeedles(KnittingKeyword quietly, Object[] needles) {		builder.'use-needles' (silent:true) {			needles.each { id ->			builder.needle ref:id }		}	}		def useNeedle(needle) { useNeedles(needle) }	def useNeedle(needle, KnittingKeyword quietly) { useNeedles(quietly, needle) }	def useNeedle(KnittingKeyword quietly, needle) { useNeedles(quietly, needle) }		def arrangeStitches(Map needles) {		builder.'arrange-stitches-on-needles' {			needles.each { e ->				builder.needle([ref:e.key], e.value)			}		}	}		def labelNeedle(String needleId, KnittingKeyword with, String text) {		if (with == KnittingKeyword.WITH_KEY) {			builder.'label-needle' (ref:needleId,'message-key':text)		} else {			// assume text is a label			builder.'label-needle' (ref:needleId,'label':text)		}	}		def repeat (Object[] args) {		// shortcut for repeatInstruction		if (args[0] instanceof String) {			return repeatInstruction(args)		}		def cl		def attrs = [:]		boolean beforeFlag = false		Repeat.Until until = null		args.each {			if (it instanceof Repeat.Until) {				until = it 				if (beforeFlag) {					until = addBeforeDescriptor(until)				}				attrs.put('until',EnumUtils.fromEnum(until))			} else if (it instanceof KnittingKeyword && (it == before || it == from)) {				beforeFlag = true				if (until != null) {					until = addBeforeDescriptor(until)					attrs.put('until',EnumUtils.fromEnum(until))				}			} else if (it instanceof Number) {				attrs.put('value',it)			} else if (it instanceof Closure) {				cl = it			}		}		builder.'repeat'(attrs, cl)	}		private Repeat.Until addBeforeDescriptor(Repeat.Until oldUntil) {		if (oldUntil == end) return beforeEnd		if (oldUntil == gap) return beforeGap		if (oldUntil == marker) return beforeMarker		return oldUntil	}		def sl (Object[] args) {		Number number		Map attrs = [:]		Repeat.Until until		boolean toFlag = false		boolean fromFlag = false		boolean holderFlag = false		String holderId = null		args.each {			if (it instanceof Number) {				number = it			} else if (it instanceof String) {				holderId = it			} else if (it instanceof Map) {				attrs = it			} else if (it instanceof KnittingKeyword) {				if (it == holder) {					holderFlag = true				} else if (it == to) {					toFlag = true				} else if (it == from) {					fromFlag = true				} else if (it == reverse) {					attrs.put('direction',EnumUtils.fromEnum(it))				}			} else if (it instanceof Wise) {				attrs.put('type',EnumUtils.fromEnum(it))			} else if (it instanceof YarnPosition) {				attrs.put('yarn-position', EnumUtils.fromEnum(it))			}		}		if (holderId != null) {			if (toFlag) {				builder.'slip-to-stitch-holder'([ref:holderId], number == null ? 1 : number)			} else {				builder.'from-stitch-holder' (ref:holderId) { builder.slip(attrs, number) }			}	    } else if (args == null) {	    	builder.slip()		} else if (number == null) {			 builder.slip(attrs)		} else {			 builder.slip(attrs, number)		}	}		def fromHolder (String id, Closure cl) { builder.'from-stitch-holder'([ref:id],cl) }		def ns (args) {		args == null ? builder.'no-stitch'() : builder.'no-stitch'(args)	}	def repeatInstruction(Object[] args) {	    String id = args[0]	    RepeatInstruction.Until until	    Number number	    Unit unit		Closure cl = null	    for (int i = 1; i < args.length; i++) {	    	if (args[i] instanceof RepeatInstruction.Until) {	    		until = args[i]	    	} else if (args[i] instanceof Number) {	    		number = args[i]	    	} else if (args[i] instanceof Measure) {	    		unit = args[i].unit	    		number = args[i].value	    	} else if (args[i] instanceof Unit) {	    		unit = args[i]	    	} else if (args[i] instanceof Closure) {	    		cl = args[i]	    	} else if (args[i] instanceof KnittingKeyword && args[i] == remain) {	    		// we have to do this because of name clash	    		until = RepeatInstruction.Until.UNTIL_STITCHES_REMAIN	        } else if (args[i] instanceof KnittingKeyword && args[i] == fer) {	        	// as in "repeat 'blah' for 3 in"	        	until = RepeatInstruction.Until.UNTIL_MEASURES	        }	    }	    if (cl == null) {		  switch (until) {			case measures:				cl = { builder.'until-measures'('unit':unit, number) }				break			case desiredLength:				cl = { builder.'until-desired-length'()}				break			case RepeatInstruction.Until.UNTIL_STITCHES_REMAIN:				cl = { builder.'until-stitches-remain' number }				break			case additionalTimes:				cl = { builder.'additional-times' number }				break			default:				cl = {}  		  }	    }		builder.'repeat-instruction' (ref:id, cl)	}		def pickUp(Object[] args) {		Long stitches		String yarnRef		def wise		args.each {			if (it instanceof Number) {				stitches = Long.valueOf(it)			} else if (it instanceof String) {				yarnRef = it			} else if (it instanceof Wise) {				wise = it			}		}		def attrs = [:]		if (yarnRef != null) {			attrs.put 'yarn-ref',yarnRef		}		if (wise == Wise.KNITWISE) {			attrs.put 'type','knitwise'		} else if (wise == Wise.PURLWISE) {			attrs.put 'type','purlwise'		}		if (currentlyInRow) {			builder.'inline-pick-up-stitches' attrs, stitches		} else {			builder.'pick-up-stitches' attrs, stitches		}	}	def inform(Object[] args) {		Number number		args.each {			if (it instanceof Number) {				number = Long.valueOf(it)			}		}		builder.information { builder.'number-of-stitches' ('number':number) }	}		def bo(Object[] args) {		bindOff(args)	}	def bO(Object[] args) {		bindOff(args)	}	def bindOff(Object[] args) {		def attrs = [:]		Number number		boolean allStitches = false		args.each {			if (it instanceof Number) {				number = Long.valueOf(it)			} else if (it instanceof KnittingKeyword && it == KnittingKeyword.ALL_STITCHES) {				allStitches = true			} else if (it instanceof Wise && it == Wise.KNITWISE) {				attrs.put 'type','knitwise'			} else if (it instanceof Wise && it == Wise.PURLWISE) {				attrs.put 'type','purlwise'			} else if (it instanceof String) {				attrs.put 'yarn-ref', it			} else if (it instanceof Map) {				attrs.putAll it			}		}		if (allStitches) {			builder.'bind-off-all' attrs		} else {			builder.'bind-off' attrs, number		}	}		def state(Object[] args) {		def informationFunctionName = 'information'		def operationName		def attrs = [:]		boolean inRow = true		boolean unworkedSts = false				args.each {			if (it instanceof Number) { 				attrs.put('number',it)			} else if (it instanceof KnittingKeyword) {				if (it == onNeedle) {					// support needle vs row at some point					inRow = false				} else if (it == unworked) {					unworkedSts = true  				}			}		}		if (unworkedSts) {			operationName = 'number-of-unworked-stitches'		} else {			operationName = 'number-of-stitches'		}		if (currentlyInRow) {			informationFunctionName = 'followup-information'		}		builder.invokeMethod(informationFunctionName, { builder.invokeMethod(operationName, attrs) })	}}