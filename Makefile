all:
	ant -f /home/kgills/Workspace/khgbase/khgbase -Dnb.internal.action.name=build jar

run:
	java -jar /home/kgills/Workspace/khgbase/khgbase/dist/khgbase.jar

clean:
	ant -f /home/kgills/Workspace/khgbase/khgbase -Dnb.internal.action.name=clean clean