package fr.jchaline.roguelike.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Line implements Iterable<Point> {
	private List<Point> points;

	public List<Point> getPoints() {
		return points;
	}

	public Line(Point _p0, Point _p1) {
		Point p0 = _p0.clone();
		Point p1 = _p1.clone();
		
		points = new ArrayList<Point>();

		int dx = Math.abs(p1.x - p0.x);
		int dy = Math.abs(p1.y - p0.y);

		int sx = p0.x < p1.x ? 1 : -1;
		int sy = p0.y < p1.y ? 1 : -1;
		int err = dx - dy;

		while (true) {
			points.add(new Point(p0.x, p0.y, p0.z));

			if (p0.x == p1.x && p0.y == p1.y)
				break;

			int e2 = err * 2;
			if (e2 > -dx) {
				err -= dy;
				p0.x += sx;
			}
			if (e2 < dx) {
				err += dx;
				p0.y += sy;
			}
		}
	}

	@Override
	public Iterator<Point> iterator() {
		return points.iterator();
	}
}
