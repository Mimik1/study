-- fun.ads

package Fun is
	type Vector is array (Integer range <>) of Float;

	procedure Print(V: in Vector);
	procedure Random_insert(V: out Vector);
	function Is_sorted(V: Vector) return Boolean;
	procedure Sort(V: in out Vector);

end Fun;
